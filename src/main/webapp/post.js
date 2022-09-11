const batch_size = 10;
let numComments = 0

let postId = document.getElementById("post_id").getAttribute('value')

let sort_type = "sort_by_likes"

let limit_reached = false

addEventListener('load', (event) => fetchComments(true));

function noMoreComments(){
    let fetch_button = document.getElementById('fetch-button')
    fetch_button.style.visibility = 'hidden'
    limit_reached = true
}

function changeSortType(combo){
    sort_type = combo.value

    numComments = 0
    document.getElementById('comment-section').innerHTML = ''

    limit_reached = false
    let fetch_button = document.getElementById('fetch-button')
    fetch_button.style.visibility = 'visible'

    fetchComments()
}

function displayComment(comment){
    let name = comment.username
    let date = comment.date
    let text =  comment.text.replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
    let likes = comment.likes

    let com_section = document.getElementById('comment-section')
    let new_com = document.createElement('div')
    new_com.setAttribute('class', 'card comment')


    let username = document.createElement('h5')
    username.setAttribute('class', 'username')
    username.innerHTML = name

    let timestamp = document.createElement('h6')
    timestamp.setAttribute('class', 'timestamp')
    timestamp.innerHTML = date

    let text_field = document.createElement('p')
    text_field.setAttribute('class', 'text-field')
    text_field.innerHTML = text

    let likes_tag = document.createElement('h6')
    likes_tag.setAttribute('class', 'likes-tag')
    likes_tag.innerHTML = likes

    new_com.appendChild(timestamp)
    new_com.appendChild(username)
    new_com.appendChild(text_field)
    new_com.appendChild(likes_tag)

    com_section.appendChild(new_com)

    numComments++;
}

function convertToDisplay(arr){
    if(arr.length < batch_size){
        noMoreComments()
    }

    arr.forEach(displayComment)
}

function fetchComments(){
    fetch("/LoadMoreComments?post_id=" + postId + "&num_comments=" + numComments + "&sort_type=" + sort_type)
        .then(response => response.json())
        .then(arr => convertToDisplay(arr)).catch(x => console.log(x))
}

function postComment(){
    let text_content = document.getElementById("text-content").value
    document.getElementById("text-content").value = ""

    if(!validateText(text_content)){
        return
    }

    fetch("/SubmitComment", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({"text": text_content, 'post_id': postId, 'date': (new Date()).getTime().toString()})
    }).then(x => x.json()).then(displayComment)
}

function validateText(text){
    if(text === ""){
        return false
    }

    return true
}