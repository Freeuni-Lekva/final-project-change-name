<%@ page import="bandfinder.models.Band" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Tag" %>

<%@ page import="java.sql.SQLException" %>
<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.TagDAO" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.*" %>

<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page import="bandfinder.infrastructure.Injector" %>

<%
    if((request.getAttribute("extra_display")) == null){
        request.getRequestDispatcher("/bandPage").forward(request, response);
    }

    final BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
    final UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    final TagDAO tagDAO = Injector.getImplementation(TagDAO.class);

    Band band = null;
    Integer id = null;
    try{
        id = Integer.parseInt(request.getParameter("bandId"));
        band = bandDAO.getById(id);
    } catch(Exception e) {
    }
%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <style>
        button {
            font-size: 14px;
        }
    </style>

    <head>
        <title>Band Page</title>
        <script>
            function confirmLeaving() {
                if(confirm("By clicking \"OK\" you will leave the band."))
                    document.getElementById("leaveForm").submit();
            }
        </script>
    </head>
    <body>
        <h1><%=band.getName()%></h1>

        <ul>
            <%
                ArrayList<Integer> memberIds = (ArrayList)bandDAO.getBandMemberIDs(id);
                for(Integer memberId : memberIds){
                    out.println("<li>"+ userDAO.getById(memberId).getStageName() +"</li>");
                }
            %>
        </ul>

        <h2><small>Tags</small></h2>
        <ul>
            <%
                ArrayList<Integer> tagIds = (ArrayList)tagDAO.getBandTagIDs(id);
                for(Integer tagId : tagIds){
                    out.println("<li>"+ tagDAO.getById(tagId).getName() +"</li>");
                }
            %>
        </ul>

        <c:if test="${extra_display}">
        <div>
            <form method="post" action=<%= "/manageMembers.jsp?bandId=" + id %>>
                <button>Manage Members</button>
            </form>
            <form method="post" action=<%= "/editBandTags.jsp?bandId=" + id %> >
                <input type="submit" value="Edit tags"/>
            </form>
            <form id="leaveForm" method="post" action=<%= "/leaveBand?bandId=" + id %>></form>
            <form method="post" action=<%= "/bandProperties.jsp?bandId=" + id %>>
                <button>Band Properties</button>
            </form>
            <button onclick="confirmLeaving()">Leave Band</button>
        </div>
        </c:if>
    </body>
</html>