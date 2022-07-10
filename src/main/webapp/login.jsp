<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login</title>
    </head>
<body>
    <a href="/register">Register</a>
    <form method="POST" action="/login">
        <label>Username</label>
        <input type="text" name="email"/><br/>
        <label>Password</label>
        <input type="password" name="password" /><br/>
        <button type="submit">Login</button>
    </form>
</body>
</html>