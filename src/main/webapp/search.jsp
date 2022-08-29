<%@ page import="bandfinder.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.models.Band" %>
<%@ page import="bandfinder.services.TagAutoComplete" %>
<%@ page import="bandfinder.services.UserAutoComplete" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    TagAutoComplete defTags = Injector.getImplementation(TagAutoComplete.class);
    UserAutoComplete defUsers = Injector.getImplementation(UserAutoComplete.class);
%>
<html>
<head>
    <title>Search</title>
</head>
<body>
    <h1>Search</h1>
    <form action="/search">
        <input list="defaults" name="query">
        <datalist id="defaults">
        <%
            List<Tag> tags = defTags.get();
            for(Tag tag: tags){
                out.println("<option value=\""+tag.getName()+"\" />");
            }
            List<User> users = defUsers.get();
            for(User user: users){
                out.println("<option value=\""+user.getFullName()+"\" />");
            }
        %>
        </datalist>

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
