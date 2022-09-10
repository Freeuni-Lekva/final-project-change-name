async function loadMorePosts() {
    let url = servletUrl + "?userId=" + userId;
    if(bandId !== null) url += "&bandId=" + bandId;
    let lastPostFetchedId = getLastPostFetchedId();
    if(lastPostFetchedId !== null) url += "&lastPostFetchedId=" + lastPostFetchedId;
    console.log(url);
    getPostsArray(url)
        .then(displayPosts)
        .catch((error) => { console.log(error);
                            return Promise.resolve(0); });
}

function getLastPostFetchedId() {
    let lastPostFetched = document.getElementById("postsSection").lastElementChild;
    if(lastPostFetched == null) return null;
    return lastPostFetched.getAttribute("id");
}

async function getPostsArray(url) {
    try {
        let response = await fetch(url);
        if(response.status !== 200) {
            console.log(response.status, response.statusText);
            return Promise.resolve(null);
        }else {
            return response.json();
        }
    }catch(error) {
        console.log(error);
        return Promise.resolve(null);
    }
}

function displayPosts(posts) {
    if(posts === null) return;
    posts.forEach(displayEachPost);
    if(posts.length === 0 && document.getElementById("loadMoreButton") !== null)
        deleteLoadMoreButton();
    return Promise.resolve(posts.length);
}

function deleteLoadMoreButton() {
    document.getElementById("loadMoreButton").remove();
    alert("No more posts to load!");
}

function createLoadMoreButton(loadedPostsNum) {
    if(loadedPostsNum === 0) return;
    let loadMoreButtonSection = document.createElement("div");
    loadMoreButtonSection.setAttribute("class", "loadMoreButtonSection");
    let loadMoreButton = document.createElement("button");
    loadMoreButton.setAttribute("id", "loadMoreButton");
    loadMoreButton.setAttribute("class", "loadMoreButton");
    loadMoreButton.setAttribute("onclick", "loadMorePosts()");
    loadMoreButton.innerHTML = "Load More";

    loadMoreButtonSection.appendChild(loadMoreButton);
    document.getElementById("userFeed").appendChild(loadMoreButtonSection);
}

function displayEachPost(post) {
    let postAuthor = post.authorBandId == null ? post.authorUserName : post.authorBandName;
    let postText = post.text.replace(/&/g, "&amp")
        .replace(/</g, "&lt")
        .replace(/>/g, "&gt");

    document.getElementById("postsSection").innerHTML +=   "<div class=\"post\" id=\"" + post.id + "\">" +
                                "<h2 class=\"postAuthorName\">" + postAuthor + "</h2>" +
                                "<h5 class=\"postDate\">" + post.date + "</h5>" +
                                "<p class=\"postText\">" + postText + "</p>" +
                                "</div>";
}









