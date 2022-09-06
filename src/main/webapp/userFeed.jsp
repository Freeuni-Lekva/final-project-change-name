<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.PostDAO" %>
<%@ page import="bandfinder.models.User" %><%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 9/6/2022
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
  @AutoInjectable
  private PostDAO postDAO;
%>

<%
  User user = (User) session.getAttribute("user");
  int userId = user.getId();
%>

<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
