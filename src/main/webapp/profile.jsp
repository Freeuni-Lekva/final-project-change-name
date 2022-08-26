<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page import="bandfinder.dao.FollowDAO" %>
<%@ page import="bandfinder.models.Follow" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
  @AutoInjectable
  private UserDAO userDAO;

  @AutoInjectable
  private FollowDAO followDAO;
%>

<%
  ServiceValueSetter.setAutoInjectableFieldValues(this);
  User user = userDAO.getById(Integer.parseInt(request.getParameter("id")));

  User loggedUser = (User) session.getAttribute("user");
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
    <%


      Follow follow = new Follow(-1, user.getId());
      if(loggedUser != null){
        follow.setFollowerID(loggedUser.getId());

        //If the logged user is visiting their own profile
        if(loggedUser.getId() == user.getId()){
          return;
        }
      }else{
        //If the user is not logged in
        return;
      }

      if(!followDAO.followExists(follow)){
        out.println("<form action=\"FollowServlet\" method=\"post\">");
        out.println("<input type=\"submit\" value=\"Follow\"/>");
      }else if(followDAO.followExists(follow)){
        out.println("<form action=\"UnfollowServlet\" method=\"post\">");
        out.println("<input type=\"submit\" value=\"Unfollow\"/>");
      }
      out.println("<input type=\"hidden\" name=\"user_id\" value=\"" + user.getId() + "\" />");
      out.println("</form>");
    %>
  </body>
</html>
