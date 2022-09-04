<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private final UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private User currentUser;
    private User recipientUser;
%>
<%
    currentUser = (User) request.getSession().getAttribute("user");
    recipientUser = userDAO.getById(Integer.parseInt(request.getParameter("id")));
%>
<html>
    <head>
        <title>Chat with <%=recipientUser.getFullName()%>></title>
        <script>
            const currentUserID = <%=currentUser.getId()%>;
            const recipientID = <%=recipientUser.getId()%>;
        </script>
        <script src="chat.js"></script>
    </head>
    <body>
        <h1>Chat with <%=recipientUser.getFullName()%></h1>
        <form id="chat-input" method="post" onsubmit="sendMessage(); return false;">
            <input type="text" name="msg-text" id="msg-text"/>
            <input type="submit" name="send-btn" id="send-btn"/>
        </form>
        <div id="messages">
        </div>
        <button id="load" onclick="loadMore()">Load more...</button>
    </body>
</html>
