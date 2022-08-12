<%@ page import="bandfinder.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.models.Band" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
    <h1>Search</h1>
    <form action="/search">
        <input type="text" name="query">
        <button type="submit">Search</button>
    </form>

    <% if(request.getParameter("query") != null && !((String)request.getParameter("query")).isBlank()) {
        out.println("<h2>Users</h2>");
        List<User> userResults = (List<User>) request.getAttribute("user_results");
        if(!userResults.isEmpty()) {
            out.println("<ul>");
            for (User u : userResults) {
                out.println("<li><a href=\"/profile.jsp?id=" + u.getId() +
                        "\">" + u.getFullName() + "</a></li>");
            }
            out.println("</ul>");
        } else {
            out.println("No such users.");
        }
        out.println("<h2>Bands</h2>");
        List<Band> bandResults = (List<Band>) request.getAttribute("band_results");
        if(!bandResults.isEmpty()) {
            out.println("<ul>");
            for (Band b : bandResults) {
                out.println("<li> <a href=\"/bandPage.jsp?bandId=" + b.getId() +
                        "\">" + b.getName() + "</a></li>");
            }
            out.println("</ul>");
        } else {
            out.println("No such bands.");
        }
    }%>
</body>
</html>
