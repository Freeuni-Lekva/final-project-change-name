<%@ page import="bandfinder.dao.BandDAO" %>
<%@ page import="bandfinder.infrastructure.Injector" %>
<%@ page import="bandfinder.models.Band" %><%--
  Created by IntelliJ IDEA.
  User: LenovoIdeapadF5
  Date: 7/27/2022
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>

<%!
    private BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
%>

<%
    int bandId = Integer.parseInt(request.getParameter("bandId"));
    Band band = bandDAO.getById(bandId);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<style>

</style>
<head>
    <title>Band Properties</title>

    <script>
        function cancelChanges() {
            let bandNameModule = document.getElementById("bandNameModule");

            let bandName = document.createElement("label");
            bandName.setAttribute("id", "bandName");
            bandName.innerText = document.getElementById("nameField").getAttribute("value");
            bandNameModule.replaceChild(bandName, document.getElementById("nameField"));

            let editButton = document.createElement("button");
            editButton.setAttribute("id", "editNameButton");
            editButton.setAttribute("onclick", "editProperty()");
            editButton.innerText = "Edit";
            bandNameModule.replaceChild(editButton, document.getElementById("saveButton"));

            document.getElementById("cancelButton").remove();
        }
    </script>

    <script>
        function editProperty() {
            let bandNameModule = document.getElementById("bandNameModule");

            let nameField = document.createElement("input");
            nameField.setAttribute("id", "nameField");
            nameField.setAttribute("type", "text");
            nameField.setAttribute("form", "saveForm");
            nameField.setAttribute("name", "bandNewName");
            nameField.setAttribute("value", document.getElementById("bandName").innerText);
            bandNameModule.replaceChild(nameField, document.getElementById("bandName"));

            let saveButton = document.createElement("button");
            saveButton.setAttribute("id", "saveButton");
            saveButton.setAttribute("type", "submit");
            saveButton.setAttribute("form", "saveForm");
            saveButton.innerText = "Save";
            bandNameModule.replaceChild(saveButton, document.getElementById("editNameButton"))

            let cancelButton = document.createElement("button");
            cancelButton.setAttribute("id", "cancelButton");
            cancelButton.setAttribute("onclick", "cancelChanges()");
            cancelButton.innerText = "Cancel";
            bandNameModule.appendChild(cancelButton);
        }
    </script>
</head>
<body>
    <%@include  file="nav.html" %>
    <div style="flex-direction: row" id="bandNameModule">
        <label>Band Name: </label>
        <label id="bandName"><%= band.getName() %></label>
        <button id="editNameButton" onclick="editProperty()">Edit</button>
    </div>
    <form id="saveForm" method="post" action=<%= "/editBandProperties?bandId=" + bandId %>></form>
</body>
</html>
