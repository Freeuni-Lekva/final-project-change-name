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

  <body>
    <ul style="list-style: none; margin: 0; padding: 0; display: inline-flex;">
      <li>
        <h1 style="margin: 0"><%= user.getStageName() %></h1>
      </li>

      <li>
          <ul style="list-style: none">
            <li style="font-weight: bold">
              Followers
            </li>
            <li>
              <%
                out.println(followDAO.getFollowerCount(user.getId()));
              %>
            </li>
          </ul>
      </li>

      <li>
        <div>
          <ul style="list-style: none">
            <li style="font-weight: bold">
              Following
            </li>
            <li>
              <%
                out.println(followDAO.getFolloweeCount(user.getId()));
              %>
            </li>
          </ul>
        </div>
      </li>
    </ul>

    <ul style="list-style: none">
      <li>Name: <%= user.getFirstName() %></li>
      <li>Surname: <%= user.getSurname() %></li>
      <li>Email: <%= user.getEmail() %></li>
    </ul>

    <%
      Follow follow = new Follow(-1, user.getId());
      if(loggedUser != null){
        follow.setFollowerID(loggedUser.getId());

        //If the logged user is visiting their own profile
        if(loggedUser.getId() == user.getId()){
          out.println("<form action=\"editProfile.jsp\" method=\"post\" style=\"display: inline-flex; position: fixed; bottom: 3%; left: 2%\">");
          out.println("<input type=\"submit\" value=\"Edit Profile\"/>");
          out.println("</form>");
        }else{
          if(!followDAO.followExists(follow)){
            out.println("<form action=\"FollowServlet\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Follow\"/>");
          }else if(followDAO.followExists(follow)){
            out.println("<form action=\"UnfollowServlet\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Unfollow\"/>");
          }
          out.println("<input type=\"hidden\" name=\"user_id\" value=\"" + user.getId() + "\" />");
          out.println("</form>");
        }
      }
    %>
  </body>
</html>
