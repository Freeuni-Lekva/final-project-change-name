<%@ page import="bandfinder.models.*" %>
<%@ page import="bandfinder.infrastructure.*" %>
<%@ page import="bandfinder.dao.*" %>
<%@ page import="bandfinder.services.*" %>
<%@ page import="java.util.*"%>
<%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 9/10/2022
  Time: 11:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private PostDAO postDAO = Injector.getImplementation(PostDAO.class);
    private InvitationDAO invitationDAO = Injector.getImplementation(InvitationDAO.class);
    private BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
    private RequestDAO requestDAO = Injector.getImplementation(RequestDAO.class);
    private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
%>
<%
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int userId = authenticationService.authenticate(loginToken);
    User user = userDAO.getById(userId);
%>

<html>
<head>
    <link rel="icon" href="icon.png">
    <link rel="stylesheet" href="style.css">
</head>
<header>
    <%@include  file="nav.html" %>
    <title><%= user.getStageName() %> | Notifications</title>
</header>
<body>
    <ul>
        <%
            List<Invitation> allInvs = invitationDAO.getAll();
            for(Invitation inv : allInvs){
                if(inv.getUserId()!=userId||inv.isProcessed()) continue;
                out.println("<li> <small> <b>"+ bandDAO.getById(inv.getBandId()).getName()+"</b> invited you to join them </small>");
                out.println("<form method=\"POST\" action=/responseToInvitation>"+
                        "<button type=\"submit\">Accept</button>"+
                        "<input type=\"hidden\" name=\"bandId\" value="+inv.getBandId()+">"+
                        "<input type=\"hidden\" name=\"answer\" value="+true+">"+
                    "</form>");
                out.println("<form method=\"POST\" action=/responseToInvitation>"+
                        "<button type=\"submit\">Reject</button>"+
                        "<input type=\"hidden\" name=\"bandId\" value="+inv.getBandId()+">"+
                        "<input type=\"hidden\" name=\"answer\" value="+false+">"+
                    "</form>");
                out.println("</li>");
            }
        %>
    </ul>
    <ul>
        <%
            List<Request> allReqs = requestDAO.getAll();
            for(Request req : allReqs){
                if(req.isProcessed() || !bandDAO.isUserInBand(userId,req.getBandId())) continue;
                out.println("<li> <small> <b>"+ userDAO.getById(req.getUserId()).getFullName()+"</b> wants to join your band: <b>"+ bandDAO.getById(req.getBandId()).getName()+"</b> </small>");
                out.println("<form method=\"POST\" action=/responseToRequest>"+
                        "<button type=\"submit\">Accept</button>"+
                        "<input type=\"hidden\" name=\"bandId\" value="+req.getBandId()+">"+
                        "<input type=\"hidden\" name=\"userId\" value="+req.getUserId()+">"+
                        "<input type=\"hidden\" name=\"answer\" value="+true+">"+
                    "</form>");
                out.println("<form method=\"POST\" action=/responseToRequest>"+
                        "<button type=\"submit\">Reject</button>"+
                        "<input type=\"hidden\" name=\"bandId\" value="+req.getBandId()+">"+
                        "<input type=\"hidden\" name=\"userId\" value="+req.getUserId()+">"+
                        "<input type=\"hidden\" name=\"answer\" value="+false+">"+
                    "</form>");
                out.println("</li>");
            }
        %>
    </ul>
</body>
</html>
