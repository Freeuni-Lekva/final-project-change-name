<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.dao.PostDAO" %>
<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.Injector" %><%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 9/10/2022
  Time: 11:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
    private PostDAO postDAO = Injector.getImplementation(PostDAO.class);
    private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
%>
<%
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int userId = authenticationService.authenticate(loginToken);
    User user = userDAO.getById(userId);
%>

<html>
<head>
    <link rel="icon" href="icon.png">
    <link rel="stylesheet" href="style.css">
</head>
<header>
    <%@include  file="nav.html" %>
    <title><%= user.getStageName() %> | Notifications</title>
</header>
<body>

</body>
</html>
