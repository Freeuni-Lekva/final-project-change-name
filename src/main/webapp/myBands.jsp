<%@ page import="java.util.ArrayList" %>
<%@ page import="bandfinder.models.Band" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <title>My Bands</title>
    </head>
    <header>
        <%@include  file="nav.html" %>
    </header>
   <body>

        <h1> Your Bands </h1>

        <ul>
            <%
                ArrayList<Band> l = (ArrayList)request.getAttribute("UserBandsList");
                for(Band band : l){
                    out.println("<li><a href=\"bandPage.jsp?bandId=" + band.getId() + "\">"+band.getName()+"</a></li>");
                }
            %>
        </ul>

        <form method="post">
            <input type="submit" value="Start a new band"/>
        </form>
    </body>
</html>