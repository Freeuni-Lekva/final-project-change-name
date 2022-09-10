async function loadPosts() {
    let url = servletUrl + "?userId=" + userId;
    if (bandId !== null) url += "&bandId=" + bandId;
    let lastPostFetchedId = getLastPostFetchedId();
    if (lastPostFetchedId !== null) url += "&lastPostFetchedId=" + lastPostFetchedId;
    return getPostsArray(url).then(displayPosts)
                             .catch((error) => { alert(error);
                                                 return Promise.resolve(0);
                                                });
}

function getLastPostFetchedId() {
    let lastPostFetched = postsSection.lastElementChild;
    if(lastPostFetched == null) return null;
    return lastPostFetched.getAttribute("id");
}

async function getPostsArray(url) {
    try {
        let response = await fetch(url);
        if(response.status !== 200) {
            alert("Error " + response.status + "\n" + response.statusText);
            return Promise.resolve(null);
        }else {
            return response.json();
        }
    }catch(error) {
        alert(error);
        return Promise.resolve(null);
    }
}

function displayPosts(posts) {
    if(posts === null) return Promise.resolve(0);

    posts.forEach(displayEachPost);
    let loadMoreButton = document.getElementById("loadMoreButton");
    if(posts.length === 0 && loadMoreButton !== null) {
        loadMoreButton.remove();
        showMessageInFeed("No more posts to show");
    }
    return Promise.resolve(posts.length);
}


function checkLoadedPosts(loadedPostsNum) {
    if(loadedPostsNum === 0) {
        showMessageInFeed("No posts to show");
    }else {
        createLoadMoreButton();
    }
}

function displayEachPost(post) {
    let postAuthor = post.authorBandId === null ? post.authorUserName : post.authorBandName;
    let postText = post.text.replace(/&/g, "&amp")
        .replace(/</g, "&lt")
        .replace(/>/g, "&gt");

    let authorUrl = post.authorBandId === null ?
        "/profile.jsp?id=" + post.authorUserId : "/bandPage.jsp?bandId=" + post.authorBandId;

    postsSection.innerHTML +=   "<div class=\"card post\" id=\"" + post.id + "\">" +
                                "<a class=\"postAuthorName\" href=\""+ authorUrl + "\">" + postAuthor + "</a>" +
                                "<h5 class=\"postDate\">" + post.date + "</h5>" +
                                "<p class=\"postText\">" + postText + "</p>" +
                                "</div>";
}

function createLoadMoreButton() {
    let loadMoreButtonSection = document.createElement("div");
    loadMoreButtonSection.setAttribute("class", "loadMoreButtonSection");
    let loadMoreButton = document.createElement("button");
    loadMoreButton.setAttribute("id", "loadMoreButton");
    loadMoreButton.setAttribute("class", "loadMoreButton");
    loadMoreButton.setAttribute("onclick", "loadPosts()");
    loadMoreButton.innerHTML = "Show More";

    loadMoreButtonSection.appendChild(loadMoreButton);
    feed.appendChild(loadMoreButtonSection);
}

function showMessageInFeed(message) {
    feed.innerHTML += "<div class=\"noPostsLoaded\">" +
        message +
        "</div>";
}











