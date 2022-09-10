<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>Register</title>
    </head>
<body>
<img src="logo.png" style="width: 33%; border-radius: 20px;
 margin-left: auto; margin-right: auto; display: block; margin-bottom: 30px" alt="Bandfinder">
<div class="card">
    <h2 style="text-align: center">Welcome to bandfinder!</h2>
    <label>Email</label>
    <input type="text" name="email" form="register-form"/><br/>
    <label>Password</label>
    <input type="password" name="password" form="register-form"/><br/>

    <label>First name</label>
    <input type="text" name="firstName" form="register-form"/><br/>

    <label>Surname</label>
    <input type="text" name="surname" form="register-form"/><br/>

    <label>Stage name</label>
    <input type="text" name="stageName" form="register-form"/><br/>
    <form id="register-form" method="POST" action="/register" style="display: inline-block;">
        <button type="submit">Register</button>
    </form>
    <form action="/login.jsp" style="display: inline-block;">
        <input type="submit" value="Login instead" />
    </form>
</div>
</body>
</html>