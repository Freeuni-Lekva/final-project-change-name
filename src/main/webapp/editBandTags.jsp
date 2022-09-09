<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page import="bandfinder.dao.TagDAO" %>
<%@ page import="bandfinder.services.TagAutoComplete" %>
<%@ page import="bandfinder.infrastructure.AutoInjectable" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.models.Tag" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<%@ page import="bandfinder.infrastructure.Constants" %>
<%@ page import="bandfinder.services.AuthenticationService" %>

<%!
    private final AuthenticationService authenticationService = Injector.getImplementation(AuthenticationService.class);
    private BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
    private UserDAO userDAO = Injector.getImplementation(UserDAO.class);
    private TagDAO tagDAO = Injector.getImplementation(TagDAO.class);
%>

<%
    String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
    int loggedInUserId = authenticationService.authenticate(loginToken);
    User user = userDAO.getById(loggedInUserId);
    int bandId = Integer.parseInt(request.getParameter("bandId"));
    TagAutoComplete defTags = Injector.getImplementation(TagAutoComplete.class);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage tags</title>

    <script>
        function confirmRemoval() {
            let tags = document.getElementById("tagsListModule").children;
            for(let i = 0; i < tags.length; i++) {
                if(tags[i].checked) {
                    if(confirm("By clicking \"OK\" marked tags will be removed from the band."))
                        document.getElementById("removeForm").submit();
                    return;
                }
            }
            alert("You haven't marked any tags.");
        }
    </script>

    <script>
        function removeTags() {
            let listTag = document.getElementById("tagsListModule");
            let tags = listTag.children;
            let length = tags.length;
            for(let i = 0; i < length; i++) {
                let checkbox = document.createElement("input");
                checkbox.setAttribute("form", "removeForm");
                checkbox.setAttribute("type", "checkbox");
                checkbox.setAttribute("id", tags[0].id);
                checkbox.setAttribute("name", tags[0].getAttribute("name") + "@");
                checkbox.setAttribute("value", tags[0].id);
                let label = document.createElement("label");
                label.setAttribute("for", tags[0].id);
                label.innerText = tags[0].innerText;
                let space = document.createElement("br");
                listTag.append(checkbox, label, space);
                tags[0].remove();
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
<body>
    <div id="tagsListModule">
        <%
            List<Integer> tagIds = tagDAO.getBandTagIDs(bandId);
            for(Integer tagId : tagIds){
                String name = tagDAO.getById(tagId).getName();
                out.println("<li name=\"" + name + "\" id=\"" + tagId +"\">" + name + "</li>");
            }
        %>
    </div>

    <div id="editTagsModule">
        <div id="buttonsModule">
            <button id="removeButton" onclick="removeTags()">Remove</button>
        </div>

        <form id="removeForm" method="post" action=<%= "/removeBandTags?bandId=" + bandId %>></form>

        <form id="cancelForm" method="post" action=<%= "/editBandTags.jsp?bandId=" + bandId %>></form>
    </div>

    <script>
        let tags = document.getElementById("tagsListModule").children;
        if(tags.length === 0) {
            document.getElementById("editTagsModule").remove();
            let message = document.createElement("label");
            message.innerText = "No tags to show.";
            document.body.appendChild(message);
        }
    </script>

    <form id="tagEnterForm" method="POST" action="/addBandTag">

        <input list="defaultTags" name="tagName">
        <datalist id="defaultTags">
        <%
            List<Tag> tags = defTags.get();
            for(Tag tag: tags){
                //System.out.println(tag.getName());
                out.println("<option value=\""+tag.getName()+"\" />");
            }
        %>
        </datalist>

        <button type="submit">Add tag</button>

        <input type="hidden" name="bandId" id="addTagButton" value=<%= bandId %>>

    </form>

</body>
</html>