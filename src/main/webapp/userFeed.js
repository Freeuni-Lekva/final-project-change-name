async function loadPosts(userId) {
    let lastPostFetchedId = getLastPostFetchedId();
    let url = "/fetchUserFeedPosts?userId=" + userId;
    if(lastPostFetchedId != null) url += "&lastPostFetchedId=" + lastPostFetchedId;
    let posts = await getPostsArray(url);
    if(posts == null) return;
    posts.forEach(displayPost);
}

function getLastPostFetchedId() {
    let postsSection = document.getElementById("postsSection");
    let lastPostFetched = postsSection.lastElementChild;
    if(lastPostFetched == null) return null;
    return lastPostFetched.getAttribute("id");
}

async function getPostsArray(url) {
    try {
        let response = await fetch(url);
        if(response.status !== 200) {
            console.log(response.status, response.statusText);
            return null;
        }else {
            return response.json();
        }
    }catch(error) {
        alert(error.message);
    }
}

function displayPost(post) {
    let postsSection = document.getElementById("postsSection");

    let newPostSection = document.createElement("div");
    newPostSection.setAttribute("id", post.id);
    newPostSection.setAttribute("class", "post");

    let textSection = document.createElement("div");
    textSection.setAttribute("class", "postText");
    textSection.innerHTML = post.text.replace(/&/g, "&amp")
        .replace(/</g, "&lt").replace(/>/g, "&gt");

    let bandPropertiesSection = document.createElement("div");
    bandPropertiesSection.setAttribute("class", "bandProperties");
    bandPropertiesSection.innerHTML = post.authorUserName + " " + post.date;

    newPostSection.appendChild(bandPropertiesSection);
    newPostSection.appendChild(textSection);
    postsSection.appendChild(newPostSection);
}






