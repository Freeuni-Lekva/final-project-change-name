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
        <title>Band Page</title>
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

        <form method="post" action=<%= "/editBandTags.jsp?bandId=" + id %> >
            <input type="submit" value="Edit tags"/>
        </form>
    </body>
</html>