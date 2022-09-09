<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="java.util.List" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="java.io.IOException" %>
<%@ page import="bandfinder.services.AuthenticationService" %>
<%@ page import="bandfinder.infrastructure.Constants" %>

<%!
    private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
    private final BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
    private final UserDAO userDAO = Injector.getImplementation(UserDAO.class);
%>

<%
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int loggedInUserId = authenticationService.authenticate(loginToken);
    User user = userDAO.getById(loggedInUserId);
    int bandId = Integer.parseInt(request.getParameter("bandId"));
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" href="icon.png">
    <title>Manage Members</title>

    <script>
        function confirmRemoval() {
            let members = document.getElementById("membersListModule").children;
            for(let i = 0; i < members.length; i++) {
                if(members[i].checked) {
                    if(confirm("By clicking \"OK\" marked members will be removed from the band."))
                        document.getElementById("removeForm").submit();
                    return;
                }
            }
            alert("You haven't marked anyone.");
        }
    </script>

    <script>
        function removeMembers() {
            let listTag = document.getElementById("membersListModule");
            let members = listTag.children;
            let length = members.length;
            for(let i = 0; i < length; i++) {
                let checkbox = document.createElement("input");
                checkbox.setAttribute("form", "removeForm");
                checkbox.setAttribute("type", "checkbox");
                checkbox.setAttribute("id", members[0].id);
                checkbox.setAttribute("name", members[0].getAttribute("name") + "@");
                checkbox.setAttribute("value", members[0].id);

                let label = document.createElement("label");
                label.setAttribute("for", members[0].id);
                label.innerText = members[0].innerText;

                let space = document.createElement("br");

                listTag.append(checkbox, label, space);
                members[0].remove();
            }
            let doneButton = document.createElement("button");
            doneButton.innerText = "Done";
            doneButton.setAttribute("onclick", "confirmRemoval()");
            document.getElementById("buttonsModule").replaceChild(doneButton, document.getElementById("removeButton"));

            let cancelButton = document.createElement("button");
            cancelButton.innerText = "Cancel";
            cancelButton.setAttribute("form", "cancelForm");
            document.getElementById("buttonsModule").appendChild(cancelButton);
        }
    </script>
</head>
<header>
    <%@include  file="nav.html" %>
</header>
<body>
    <div id="membersListModule">
        <%
            List<Integer> memberIds = bandDAO.getBandMemberIDs(bandId);
            for(Integer memberId : memberIds){
                if(memberId == user.getId()) continue;
                String stageName = userDAO.getById(memberId).getStageName();
                String email = userDAO.getById(memberId).getEmail();
                out.println("<li name=\"" + email + "\" id=\"" + memberId +"\">" + stageName + "</li>");
            }
        %>
    </div>

    <div id="editMembersModule">
        <div id="buttonsModule">
            <button id="removeButton" onclick="removeMembers()">Remove</button>
        </div>

        <form id="removeForm" method="post" action=<%= "/removeMembers?bandId=" + bandId %>></form>

        <form id="cancelForm" method="post" action=<%= "/manageMembers.jsp?bandId=" + bandId %>></form>
    </div>

    <script>
        let members = document.getElementById("membersListModule").children;
        if(members.length === 0) {
            document.getElementById("editMembersModule").remove();
            let message = document.createElement("label");
            message.innerText = "No band members to show but you.";
            document.body.appendChild(message);
        }
    </script>
</body>
</html>
