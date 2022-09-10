const batch_size = 10;
let currBatch = 0

addEventListener('load', (event) => fetchComments());

function noMoreComments(){
    let fetch_button = document.getElementById('fetch-button')
    fetch_button.remove()
}

function displayComment(comment){
    let name = comment.username
    let date = comment.date
    let text = comment.text
    let likes = comment.likes

    let com_section = document.getElementById('comment-section')
    let new_com = document.createElement('div')
    new_com.setAttribute('class', 'comment')

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
}

function convertToDisplay(arr){
    if(arr.size < batch_size){
        noMoreComments()
    }

    arr.forEach(displayComment)
}

function fetchComments(){
    let postId = document.getElementById("post_id").getAttribute('value')

    let sort_type = 'sort_by_likes'

    fetch("/LoadMoreComments?post_id=" + postId + "&batch_num=" + currBatch + "&sort_type=" + sort_type)
        .then(response => response.json())
        .then(convertToDisplay).catch(x => console.log(x))

    currBatch++;
}