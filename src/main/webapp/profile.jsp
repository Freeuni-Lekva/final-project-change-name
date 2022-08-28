<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.TagDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
  @AutoInjectable
  private UserDAO userDAO;

    @AutoInjectable
    private TagDAO tagDAO;
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
          <h2><small>Tags</small></h2>
          <ul>
              <%
                  ArrayList<Integer> tagIds = (ArrayList)tagDAO.getUserTagIDs(user.getId());
                  for(Integer tagId : tagIds){
                      out.println("<li>"+ tagDAO.getById(tagId).getName() +"</li>");
                  }
              %>
          </ul>

          <form method="post" action=<%= "/editUserTags.jsp?userId=" + user.getId() %> >
              <input type="submit" value="Edit tags"/>
          </form>
  </body>
</html>
