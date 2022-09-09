<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="java.nio.file.FileStore" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%!
        private AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
        private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    %>
    <%
        String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User user = userDAO.getById(loggedInUserId);

        String passwordIncorrectMessage = (String) session.getAttribute("passwordIncorrect");
    %>

    <%!
        private boolean passwordIsIncorrect(String message){
            if(message == null){
                return false;
            }
            return true;
        }
    %>

    <title>Profile</title>
</head>
<body>
        <%@include  file="nav.html" %>
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

    <%
        if(passwordIsIncorrect(passwordIncorrectMessage)){
            out.println("<div style=\"color: darkred\">" + passwordIncorrectMessage + "</div>");
        }
    %>

    <button id="password-button" onmouseup="selectBottom()">Edit Password</button>

    <script src="editProfile.js"></script>
</body>
</html>
