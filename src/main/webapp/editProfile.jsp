<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%
        User user = (User) session.getAttribute("user");
    %>

    <title>Profile</title>
</head>
<body>
    <form action="EditProfileServlet" method="post" id="user-info">
        <ul style="list-style-type: none">
            <li>
                <div>
                    Your email:
                    <div id="email"><%= user.getEmail() %></div>
                    <input id="hidden-email" type="hidden" name="email" value=<%= user.getEmail() %>>
                </div>
            </li>
            <li>
                <div>
                    Your name:
                    <div id="name"><%= user.getFirstName() %></div>
                    <input id="hidden-name" type="hidden" name="name" value=<%= user.getFirstName() %>>
                </div>
            </li>
            <li>
                <div>
                    Your surname:
                    <div id="surname"><%= user.getSurname() %></div>
                    <input id="hidden-surname" type="hidden" name="surname" value=<%= user.getSurname() %>>
                </div>
            </li>
            <li>
                <div>
                    Your stage name:
                    <div id="stageName"><%= user.getStageName() %></div>
                    <input id="hidden-stageName" type="hidden" name="stageName" value=<%= user.getStageName() %>>
                </div>
            </li>
        </ul>
        <button onmouseup="selectTop()" id="edit-button">Edit</button>
    </form>

    <br>

    <button id="password-button" onmouseup="selectBottom()">Edit Password</button>

    <script src="editProfile.js"></script>
</body>
</html>
