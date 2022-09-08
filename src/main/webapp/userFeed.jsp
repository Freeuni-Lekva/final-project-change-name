<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.PostDAO" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.models.Band" %><%--
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
%>

<%
  User user = (User) session.getAttribute("user");
  int userId = user.getId();
  int lastPostFetchedId = -1;
%>

<html>
<head>
    <script src="userFeed.js"></script>
    <link rel="stylesheet" href="feedStyle.css">

    <title>Newsfeed</title>
</head>
<body>
    <h1 style="position: fixed" id="user"><%=user.getStageName()%></h1>

    <div class="feed" id="feed">
        <div class="postsSection" id="postsSection">
        </div>
        <div id="loadMoreButtonSection">
            <button onclick="loadPosts(<%=userId%>)">Load More</button>
        </div>
    </div>
    <script>
        loadPosts(<%=userId%>);
    </script>
</body>
</html>
