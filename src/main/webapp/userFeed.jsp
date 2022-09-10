<%@ page import="bandfinder.models.*" %>
<%@ page import="bandfinder.infrastructure.*" %>
<%@ page import="bandfinder.dao.*" %>
<%@ page import="bandfinder.services.*" %>
<%@ page import="java.util.*"%>
<%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 9/6/2022
  Time: 6:20 PM
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
    <script src="feed.js"></script>
    <link rel="stylesheet" href="style.css">

    <title>Newsfeed</title>
</head>
<header>
    <%@include  file="nav.html" %>
</header>
<body>
    <h1 style="position: fixed"><%=user.getFullName()%></h1>
    <%------FEED---------%>
    <div class="feed" id="feed">
        <div class="postsSection" id="postsSection">
        </div>
    </div>
    <script>
        const feed = document.getElementById("feed");
        const postsSection = document.getElementById("postsSection");
        const postsToFetchMaxNum = 5;
        const userId = <%=userId%>;
        const bandId = null;
        const servletUrl = "/fetchUserFeedPosts";
        loadPosts().then(checkLoadedPosts);
    </script>
    <script src="feed.js"></script>
    <%-------FEED--------%>
</body>
</html>
