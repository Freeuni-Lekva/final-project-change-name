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
    <%
      User loggedInUser = (User) request.getSession().getAttribute("user");
      if(loggedInUser != null) {
        if(loggedInUser.equals(user)){
          out.println("<form action=\"editProfile\" method=\"post\">\n" +
                  "      <input type=\"submit\" value=\"Edit Profile\"/>\n" +
                  "    </form>");
        } else {
          out.println("<form action=\"/chat.jsp\" method=\"get\">\n" +
                  "    <input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\"/>\n" +
                  "    <input type=\"submit\" value=\"Chat\"/>\n" +
                  "  </form>");
        }
      }
    %>


  <body>
  
  </body>
</html>
