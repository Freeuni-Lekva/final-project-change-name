<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.infrastructure.ServiceValueSetter" %>
<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.models.Band" %>
<%!
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;
%>
<html>
    <body>
        <h2>Hello World!</h2>
        <h3>Users:</h3>
        <ul>
            <%
                ServiceValueSetter.setAutoInjectableFieldValues(this);
                List<User> users = userDAO.getAll();
                for(User u : users) {
                    out.println("<li> <a href=\"/profile.jsp?id=" + u.getId() +
                            "\">" + u.getFullName() + "</a></li>");
                }
            %>
        </ul>
        <h3>Bands:</h3>
        <ul>
            <%
                List<Band> bands = bandDAO.getAll();
                for(Band b : bands) {
                    out.println("<li> <a href=\"/bandPage.jsp?bandId=" + b.getId() +
                            "\">" + b.getName() + "</a></li>");
                }
            %>
        </ul>
        <br><br>
        <i>
            <%
                User loggedInUser = (User) request.getSession().getAttribute("user");
                if(loggedInUser != null) {
                    out.println("Logged in as: " + loggedInUser.getFullName());
                } else {
                    out.println("You are not logged in.");
                }
            %>
        </i>
        <br>
        <a href="/register.jsp">
            <button>Register/Log in</button>
        </a>
    </body>
</html>
