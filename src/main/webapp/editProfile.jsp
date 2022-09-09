<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="java.nio.file.FileStore" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <%!
        private AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
        private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    %>
    <%
        String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User user = userDAO.getById(loggedInUserId);

        Boolean passwordIncorrect= (Boolean) session.getAttribute("passwordIncorrect");

        if(passwordIncorrect == null){
            request.setAttribute("passwordIncorrect", false);
        }
    %>

    <title>Profile</title>
</head>
<body>
<%@include  file="nav.html" %>
    <form action="EditProfileServlet" method="post" id="user-info" class="user_data_form">
        <ul style="list-style-type: none" class="user_data">
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
        <div id="btn-div">
            <button onmouseup="selectTop()" id="edit-button">Edit</button>
        </div>
    </form>

    <br>

    <div>
        <c:if test="${passwordIncorrect}">
            <div class="password-incorrect-message">
                Incorrect password!
            </div>
        </c:if>

        <button id="password-button" onmouseup="selectBottom()">Edit Password</button>
    </div>

    <div id="edit-password-div" class="edit-password-block">
        <form action="EditPasswordServlet" method="post">
            <ul>
                <li>
                    Current password:
                    <input class='pass-input' type="password" name="currentPassword" id="cur-password" />
                </li>
                <li>
                    New password:
                    <input class='pass-input' type="password" name="newPassword" id="new-password" />
                </li>
            </ul>

            <ul>
                <li>
                    <input type="submit" value="save" />
                </li>

                <li>
                    <button type="button" onmouseup="deselectBottom()"> cancel </button>
                </li>
            </ul>
        </form>
    </div>

    <script src="editProfile.js"></script>
</body>
</html>
