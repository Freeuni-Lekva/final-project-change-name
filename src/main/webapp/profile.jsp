<%@ page import="bandfinder.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) session.getAttribute("Authenticated User"); %>
<html>
  <head>
    <title><%= user.getStageName() %> | Profile</title>
  </head>
    <h1><%= user.getStageName() %></h1>
    <ul>
      <li>Name: <%= user.getFirstName() %></li
      <li>Surname: <%= user.getSurname() %></li>
      <li>Email: <%= user.getEmail() %></li>
    </ul>
    <form action="editProfile" method="post">
      <input type="submit" value="Edit Profile"/>
    </form>
  <body>
  
  </body>
</html>
