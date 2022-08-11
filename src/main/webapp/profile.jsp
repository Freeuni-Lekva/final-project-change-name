<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
  @AutoInjectable
  private UserDAO userDAO;
%>

<%
  ServiceValueSetter.setAutoInjectableFieldValues(this);
  User user = userDAO.getById(Integer.parseInt(request.getParameter("id")));
%>
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
