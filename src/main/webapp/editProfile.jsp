<%@ page import="bandfinder.models.User" %>
<%@ page import="bandfinder.dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%
        User user = (User) session.getAttribute(UserDAO.ATTRIBUTE_NAME);
    %>

    <title>Profile</title>
    <script>
        /*
            elemID -> div that contains pure text
            buttonID -> edit button

            Changes div with elemID id into a text field.
            Text field value is set to the text contained by the div.
         */
        function changeEditText(elemID, buttonID){
            const divElem = document.getElementById(elemID)
            const button = document.getElementById(buttonID)


            const textField = createInputField('text', elemID)
            textField.setAttribute('value', divElem.textContent)

            const saveButton = createSubmitButton('save')

            button.replaceWith(saveButton)
            divElem.firstChild.replaceWith(textField)
        }

        /*
            Change the button with the given buttonID into a div
            containing a form with two password input fields and a submit button.

            Two password fields are created for inputting the current and the new password.
         */
        function changePassword(buttonID){
            const divElem = document.createElement('div')
            const form = document.createElement('form')

            form.setAttribute('action', 'EditPasswordServlet')
            form.setAttribute('method', 'post')

            const curPassword = createInputField('password', 'currentPassword')
            curPassword.setAttribute('id', 'cur-password')
            const newPassword = createInputField('password', 'newPassword')
            newPassword.setAttribute('id', 'new-password')

            const curPasswordLabel = createLabelFor('cur-password', 'Current Password: ')
            const newPasswordLabel = createLabelFor('new-password', 'New Password: ')

            const saveButton = createSubmitButton('save')
            divElem.appendChild(saveButton)

            form.appendChild(curPasswordLabel)
            form.appendChild(curPassword)

            const breakLine = document.createElement('br')
            form.appendChild(breakLine)

            form.appendChild(newPasswordLabel)
            form.appendChild(newPassword)

            divElem.appendChild(form)

            const button = document.getElementById(buttonID)
            button.replaceWith(divElem)
        }

        function createLabelFor(forID, text){
            const label = document.createElement('label')
            label.setAttribute('for', forID)
            label.innerHTML = text;
            return label
        }

        function createSubmitButton(val){
            const saveButton = document.createElement('input')
            saveButton.setAttribute('type', 'submit')
            saveButton.setAttribute('value', val)
            return saveButton
        }

        function createInputField(type, name){
            const field = document.createElement('input')
            field.setAttribute('name', name)
            field.setAttribute('type', type)
            return field
        }
    </script>
</head>
<body>
    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editEmail" value="Edit" onmouseup="changeEditText('email', 'editEmail')">
        <div id="email"><%= user.getEmail() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your name</p>
        <input type="button" id="editName" value="Edit" onmouseup="changeEditText('name', 'editName')">
        <div id="name"><%= user.getFirstName() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editSurname" value="Edit" onmouseup="changeEditText('surname', 'editSurname')">
        <div id="surname"><%= user.getSurname() %></div>
    </form>

    <form action="EditProfileServlet" method="post">
        <p>Edit your stage name</p>
        <input type="button" id="editStageName" value="Edit" onmouseup="changeEditText('stageName', 'editStageName')">
        <div id="stageName"><%= user.getStageName() %></div>
    </form>

    <br>

    <button id="password-button" onmouseup="changePassword('password-button')">Edit Password</button>
</body>
</html>
