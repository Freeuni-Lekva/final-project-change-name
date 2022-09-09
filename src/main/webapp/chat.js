const initmsgcount = 30;
const msgbatchsize = 20;
const servletURL = "/GetMessagesAsJSONServlet";
let oldestMsgId;
let newestMsgId;

function getSearchParam(param) {
    let params = new URLSearchParams(location.search);
    return params.get(param);
}

function createMessageDiv(message) {
    const messageDiv = document.createElement("div");
    if(typeof oldestMsgId === 'undefined' || message.id < oldestMsgId) {
        oldestMsgId = message.id;
    }
    if(typeof newestMsgId === 'undefined' || message.id > newestMsgId) {
        newestMsgId = message.id;
    }
    messageDiv.setAttribute("id", message.id);
    messageDiv.setAttribute("class", "msg");
    messageDiv.innerHTML =
        "<b class=\"msg-sender\">" + message.senderName + "</b><br>" + message.content + " - <i class=\"msg-time\">" + message.time +"</i><br><br>";
    return messageDiv;
}

function prependMessageToChat(message) {
    const messageDiv = createMessageDiv(message);
    document.getElementById("messages").prepend(messageDiv);
}

function appendMessageToChat(message) {
    const messageDiv = createMessageDiv(message);
    document.getElementById("messages").appendChild(messageDiv);
}

function displayInit(count) {
    const params = new URLSearchParams();
    params.append("recipientId", recipientID);
    params.append("msgcount", count);
    params.append("initmsgs", true);
    fetch(servletURL,{
        method: "POST",
        body: params
    }).then(response => response.json()).then(messages => messages.forEach(appendMessageToChat));
}

function displayMessagesFrom(id, count){
    const params = new URLSearchParams();
    params.append("recipientId", recipientID);
    params.append("msgcount", count);
    params.append("initmsgs", false);
    params.append("lastmsgid", id);
    fetch(servletURL,{
        method: "POST",
        body: params
    }).then(response => response.json()).then(messages =>{
        if(messages.length < count || messages.length === 0) {
            document.getElementById("load").remove();
        }
        messages.forEach(appendMessageToChat);
    });
}

function loadMore() {
    if(typeof oldestMsgId === 'undefined'){
        displayInit(initmsgcount);
        return undefined;
    }
    displayMessagesFrom(oldestMsgId, msgbatchsize);
}

displayInit(initmsgcount);

const ws = new WebSocket("ws://localhost:8080/chatWebSocket"); // Server IP here

ws.onopen = function() {
    ws.send(JSON.stringify({
        "isInfoMsg" : true,
        "senderToken" : loginToken,
        "receiverId" : recipientID,
        "content" : ""
    }));
    ws.onmessage = function(messageEvent) {
        const message = JSON.parse(messageEvent.data);
        prependMessageToChat(message);
    }
}

function sendMessage() {
    const msgBox = document.getElementById("msg-text")
    const content = msgBox.value;
    const newMessage = {
        "isInfoMsg": false,
        "senderToken" : loginToken,
        "receiverId": recipientID,
        "content": content
    };
    msgBox.value = "";
    ws.send(JSON.stringify(newMessage));
}