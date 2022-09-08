async function loadPosts(userId) {
    let lastPostFetchedId = getLastPostFetchedId();
    let url = "/fetchUserFeedPosts?userId=" + userId;
    if(lastPostFetchedId != null) url += "&lastPostFetchedId=" + lastPostFetchedId;
    let responseText = await requestPosts(url);

}

function getLastPostFetchedId() {
    let postsSection = document.getElementById("postsSection");
    let lastPostFetched = postsSection.lastElementChild;
    if(lastPostFetched == null) return null;
    return lastPostFetched.getAttribute("id");
}

async function requestPosts(url) {
    try {
        let response = await fetch(url);
        if(response.status != 200) console.log(response.status, response.statusText);
        return response.text();
    }catch(error) {
        console.log(error);
    }
}






