<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>Login</title>
    </head>
<header>
    <%@include  file="nav.html" %>
</header>
<body>
    <a href="/register.jsp">Register</a>
    <form method="POST" action="/login">
        <label>Username</label>
        <input type="text" name="email"/><br/>
        <label>Password</label>
        <input type="password" name="password" /><br/>
        <button type="submit">Login</button>
    </form>
</body>
</html>