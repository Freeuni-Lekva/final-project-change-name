<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>Login</title>
    </head>
<body>
<img src="logo.png" style="width: 33%; border-radius: 20px;
 margin-left: auto; margin-right: auto; display: block; margin-bottom: 30px" alt="Bandfinder">
<div class="card">
    <label>Username</label>
    <input type="text" name="email" form="login-form"/><br/>
    <label>Password</label>
    <input type="password" name="password" form="login-form"/><br/>
    <form id="login-form" method="POST" action="/login" style="display: inline-block;">
        <button type="submit">Login</button>
    </form>
    <form action="/register.jsp" style="display: inline-block;">
        <input type="submit" value="Register instead" />
    </form>
</div>
</body>
</html>