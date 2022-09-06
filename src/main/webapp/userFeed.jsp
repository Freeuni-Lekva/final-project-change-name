<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.PostDAO" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.dao.BandDAO" %><%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 9/6/2022
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private PostDAO postDAO = Injector.getImplementation(PostDAO.class);
%>

<%
  User user = (User) session.getAttribute("user");
  int userId = user.getId();
%>

<html>
<style>
    #feedPostsSection {
        margin: auto;
        width: 500px;
        background-color: Gray;
        border: 1px solid DodgerBlue;
        border-radius: 12px;
        padding: 20px;
    }
    .post {
        margin: auto;
        margin-top: 10px;
        margin-bottom: 10px;
        width: 450px;
        background-color: LightGray;
        border: 1px solid DodgerBlue;
        border-radius: 12px;
        padding: 10px;
    }
    .postText {
        margin: auto;
        width: 400px;
        word-wrap: break-word;
    }

    .postProperties {
        display: inline;
    }

</style>
<head>
    <title>Newsfeed</title>
</head>
<body>
    <h1><%= user.getStageName() %></h1>
    <div id="feedPostsSection">
        <%
            List<Post> userFeedPosts = postDAO.getUserFeedNewestPosts(userId, 10);
            for(Post post : userFeedPosts) {
                out.println("<div class=\"post\">" +
                                "<div class=\"postProperties\">" + userDAO.getById(post.getAuthorUser()).getStageName() + "</div>" +
                                "<div class\"postText\">" + post.getText() + "</div>" +
                            "</div>");
            }
        %>
    </div>
</body>
</html>
