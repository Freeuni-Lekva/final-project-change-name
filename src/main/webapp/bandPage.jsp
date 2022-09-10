<%@ page import="bandfinder.models.Band" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Tag" %>

<%@ page import="java.sql.SQLException" %>
<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.TagDAO" %>
<%@ page import="bandfinder.infrastructure.*" %>
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
    <head>
        <script src="feed.js"></script>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>Band Page</title>
        <script>
            function confirmLeaving() {
                if(confirm("By clicking \"OK\" you will leave the band."))
                    document.getElementById("leaveForm").submit();
            }
        </script>
    </head>

    <header>
        <%@include  file="nav.html" %>
    </header>
    <body>
    <div class="card">
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

        <c:if test="${!extra_display && !isPending}">
            <div>
                <form method="post" action=<%= Constants.URL_JOIN_BAND_REQUEST+"?bandId=" + id %>>
                    <button>Send join request</button>
                </form>
            </div>
        </c:if>
        <c:if test="${isPending}">
            <div>
                <button>Join request pending...</button>
            </div>
        </c:if>

    </div>

    <div class="card">
        <form method="post" action="/addPost" name="Add Post">
            <input type="hidden" name="post-type" value="band">
            <input type="hidden" name="band-id" value="<%=id%>">
            <input name="post-content">
            <input type="submit" value="Add Post">
        </form>
    </div>

    <%------------FEED-----------%>
    <div class="feed" id="feed">
        <div class="postsSection" id="postsSection">
        </div>
    </div>
    <script>
        const feed = document.getElementById("feed");
        const postsSection = document.getElementById("postsSection");
        const userId = <%=id%>;
        const bandId = <%=band.getId()%>;
        const servletUrl = "/fetchBandPosts";
        loadPosts().then(checkLoadedPosts);
    </script>
    <%------------FEED-----------%>
    
    </body>
</html>