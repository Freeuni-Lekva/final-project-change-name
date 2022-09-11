<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page import="bandfinder.dao.PostDAO" %>
<%@ page import="bandfinder.models.Post" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <%
        int post_id = -1;
        try {
            post_id = Integer.parseInt(request.getParameter("post_id"));
        }
        catch (NumberFormatException e){
            response.sendRedirect("errorPage.html");
        }

        if(request.getAttribute("servlet_loaded") == null){
            request.getRequestDispatcher("/comments?post_id=" + post_id);
        }

        UserDAO userDAO = Injector.getImplementation(UserDAO.class);
        Post post = (Post) request.getAttribute("post");
    %>
    <title>comments</title>
</head>

<header>
    <%@include  file="nav.html" %>
</header>
<body>
    <input type="hidden" id="post_id" value= <%= post_id %> />

    <div class="comment">
        <h1> Author: <%= userDAO.getById(post.getAuthorUser()).getStageName() %> </h1>
        <p> Date posted: <%= post.getDate().toString() %> </p>
        <p> <%= post.getText() %> </p>
    </div>
    <div class="card comment-field">
    <c:if test="${logged_in}">
        <div>
            <input type="text" name="comment" id="text-content"/>
            <button type="button" onmouseup="postComment()">Submit</button>
        </div>
    </c:if>
        <select id="select-sort-type" onchange="changeSortType(this)">
            <option value="sort_by_likes">Most liked</option>
            <option value="sort_by_date">Newest first</option>
        </select>
    </div>

    <div>

        <div id="comment-section" class="comment-section"></div>
        <button id="fetch-button" type="button" onmouseup="fetchComments()">Load More</button>
    </div>
    <script src="post.js"></script>
</body>
</html>
