<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        int postId = Integer.valueOf(request.getParameter("postId"));
    %>
    <title>Post</title>
</head>
<body>
    <input type="hidden" id="post_id" value= <%= postId %> />
    <div>
        <h1>POST BODY</h1>
    </div>

    <div class="comment-field">
        <input type="text" name="comment" id="text-content"/>
        <button type="button" onmouseup="postComment()">Submit</button>
    </div>

    <div>
        <select id="select-sort-type" onchange="changeSortType(this)">
            <option value="sort_by_likes">Most liked</option>
            <option value="sort_by_date">Newest first</option>
        </select>
        <div id="comment-section" class="comment-section"></div>
        <button id="fetch-button" type="button" onmouseup="fetchComments()">Load More</button>
    </div>

    <script src="post.js"></script>
</body>
</html>
