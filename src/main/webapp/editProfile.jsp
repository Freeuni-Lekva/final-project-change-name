<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <script>
        function changeEditText(elemID, buttonID){
            const divElem = document.getElementById(elemID)
            const button = document.getElementById(buttonID)


            const textField = document.createElement('input')
            textField.setAttribute('name', elemID)
            textField.setAttribute('value', divElem.textContent)

            const saveButton = document.createElement('input')
            saveButton.setAttribute('type', 'submit')
            saveButton.setAttribute('name', elemID + 'Button')
            saveButton.setAttribute('value', 'save')
            button.replaceWith(saveButton)

            divElem.firstChild.replaceWith(textField)
        }
    </script>
</head>
<body>
    <form action="EditProfileServlet" method="post">
        <p>Edit your name</p>
        <input type="button" id="editName" value="Edit" onmouseup="changeEditText('name', 'editName')">
        <div id="name">My Name</div>
    </form>
    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editStageName" value="Edit" onmouseup="changeEditText('stageName', 'editStageName')">
        <div id="stageName">My Stage Name</div>
    </form>
</body>
</html>
