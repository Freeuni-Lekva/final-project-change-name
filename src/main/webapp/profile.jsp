<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bandfinder.models.*" %>
<%@ page import="bandfinder.infrastructure.*" %>
<%@ page import="bandfinder.dao.*" %>
<%@ page import="bandfinder.services.*" %>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
  @AutoInjectable
  private UserDAO userDAO;
  @AutoInjectable
  private TagDAO tagDAO;
  @AutoInjectable
  private FollowDAO followDAO;

    @AutoInjectable
    private BandDAO bandDAO;
    @AutoInjectable
    private InvitationDAO invitationDAO;

  private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
  private User user;
%>

<%
  ServiceValueSetter.setAutoInjectableFieldValues(this);
  if(request.getParameter("id") == null){
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int loggedInUserId = authenticationService.authenticate(loginToken);
    user = userDAO.getById(loggedInUserId);
  }else{
    if(request.getAttribute("following") == null){
      request.getRequestDispatcher("LoadUserProfileServlet").forward(request, response);
    }
    user = userDAO.getById(Integer.parseInt(request.getParameter("id")));
  }
%>
<html>
  <head>
    <title><%= user.getStageName() %> | Profile</title>
  </head>

  <body>
    <%@include  file="nav.html" %>
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
    <h2><small>Tags</small></h2>
    <ul>
        <%
            ArrayList<Integer> tagIds = (ArrayList)tagDAO.getUserTagIDs(user.getId());
            for(Integer tagId : tagIds){
                out.println("<li>"+ tagDAO.getById(tagId).getName() +"</li>");
            }
        %>
    </ul>


    <c:if test="${loggedUser}">
      <c:choose>
        <c:when test="${sameUser}">
          <form action="editProfile.jsp" method="post" style="display: inline-flex; position: fixed; bottom: 3%; left: 2%">
            <input type="submit" value="Edit Profile"/>
          </form>
          <form method="post" action=<%= "/editUserTags.jsp?userId=" + user.getId() %> >
              <input type="submit" value="Edit tags"/>
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

          <form action="/chat.jsp" method="get">
            <input type="hidden" name="id" value="<%=user.getId()%>"/>
            <input type="submit" value="Chat"/>
          </form>

           <form action="InviteMemberToBandServlet" method="POST">
                <label for="info">Invite member to your band</label>
                <select name="bandId" id="info" multiple>

                    <%
                        String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
                        int inviterId = authenticationService.authenticate(loginToken);
                        int targetId = user.getId();
                        List<Integer> bandIds = bandDAO.getAllBandIDsForUser(inviterId);
                        for(Integer bandId: bandIds){
                            Band band = bandDAO.getById(bandId);
                            if(bandDAO.isUserInBand(targetId,bandId)){
                                out.println("<option value=\""+band.getId()+"\">"+ band.getName() +" [already in band] </option>");
                            }else{
                                int invitationId = invitationDAO.getId(targetId,bandId);
                                if(invitationId==Constants.NO_ID || invitationDAO.getById(invitationId).isProcessed()){
                                    out.println("<option value=\""+band.getId()+"\">"+ band.getName() +"</option>");
                                }else{
                                    out.println("<option value=\""+band.getId()+"\">"+ band.getName() +" [invitation pending] </option>");
                                }
                            }
                        }
                    %>
                </select>
                <input type="submit" value="Submit" />
                <input type="hidden" name="targetId" id="targetIdContainer" value=<%= targetId %>>
          </form>

        </c:when>
      </c:choose>
    </c:if>
  </body>
</html>
