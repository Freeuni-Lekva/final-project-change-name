<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  if(request.getAttribute("following") == null){
    request.getRequestDispatcher("LoadUserProfileServlet").forward(request, response);
  }

  ServiceValueSetter.setAutoInjectableFieldValues(this);

  User user = userDAO.getById(Integer.parseInt(request.getParameter("id")));
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

    <c:if test="${loggedUser}">
      <c:choose>
        <c:when test="${sameUser}">
          <form action="editProfile.jsp" method="post" style="display: inline-flex; position: fixed; bottom: 3%; left: 2%">
            <input type="submit" value="Edit Profile"/>
          </form>
        </c:when>

        <c:when test="${!sameUser}">
          <c:choose>
            <c:when test="${!following}">
              <form action="FollowServlet" method="post">
                <input type="submit" value="Follow"/>
                <input type="hidden" name="user_id" value= <%= user.getId() %> />
              </form>
            </c:when>
            <c:when test="${following}">
              <form action="UnfollowServlet" method="post">
                <input type="submit" value="Unfollow"/>
                <input type="hidden" name="user_id" value= <%= user.getId() %> />
              </form>
            </c:when>
          </c:choose>
        </c:when>
      </c:choose>
    </c:if>
  </body>
</html>
