<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.infrastructure.*" %>
<%@ page import="bandfinder.dao.*" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%!
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;
%>
<html>
    <head>
        <title>Bandfinder</title>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
    </head>
    <header>
        <%@include  file="nav.html" %>
    </header>
    <body>
    <%
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User loggedInUser = userDAO.getById(loggedInUserId);

        if(loggedInUser == null) {
            response.sendRedirect("/register.jsp");
        } else {
            response.sendRedirect("/userFeed.jsp");
        }
    %>
    </body>
</html>
