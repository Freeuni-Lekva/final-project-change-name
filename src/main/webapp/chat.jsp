<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private final UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
    private User currentUser;
    private User recipientUser;
%>
<%
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int loggedInUserId = authenticationService.authenticate(loginToken);
    User currentUser = userDAO.getById(loggedInUserId);
    recipientUser = userDAO.getById(Integer.parseInt(request.getParameter("id")));
%>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>Chat with <%=recipientUser.getFullName()%>></title>
        <script>
            const loginToken = "<%=loginToken%>";
            const currentUserID = <%=currentUser.getId()%>;
            const recipientID = <%=recipientUser.getId()%>;
        </script>
        <script src="chat.js"></script>
    </head>
    <header>
        <%@include  file="nav.html" %>
    </header>
    <body>
        <div class="card chat-thread">
            <h1>Chat with <%=recipientUser.getFullName()%></h1>
            <form id="chat-input" method="post" onsubmit="sendMessage(); return false;">
                <input type="text" name="msg-text" id="msg-text"/>
                <input type="submit" name="send-btn" id="send-btn"/>
            </form>
            <hr>
            <div id="messages">
            </div>
            <button id="load" onclick="loadMore()">Load more...</button>
        </div>
    </body>
</html>
