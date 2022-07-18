<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%
        User user = (User) session.getAttribute(UserDAO.ATTRIBUTE_NAME);
    %>

    <title>Profile</title>
</head>
<body>
    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editEmail" value="Edit" onmouseup="changeEditText('email', 'editEmail')">
        <div id="email"><%= user.getEmail() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your name</p>
        <input type="button" id="editName" value="Edit" onmouseup="changeEditText('name', 'editName')">
        <div id="name"><%= user.getFirstName() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editSurname" value="Edit" onmouseup="changeEditText('surname', 'editSurname')">
        <div id="surname"><%= user.getSurname() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editStageName" value="Edit" onmouseup="changeEditText('stageName', 'editStageName')">
        <div id="stageName"><%= user.getStageName() %></div>
    </form>

    <br>

    <button id="password-button" onmouseup="changePassword('password-button')">Edit Password</button>

    <script src="editProfile.js"></script>
</body>
</html>
