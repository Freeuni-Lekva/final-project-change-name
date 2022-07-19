<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Register</title>
    </head>
<body>
    <a href="/login.jsp">Login</a>
    <form method="POST" action="/register">
        <label>Username</label>
        <input type="text" name="email"/><br/>
        <label>Password</label>
        <input type="password" name="password" /><br/>

        <label>First name</label>
        <input type="text" name="firstName" /><br/>

        <label>Surname</label>
        <input type="text" name="surname" /><br/>

        <label>Stage name</label>
        <input type="text" name="stageName" /><br/>

        <button type="submit">Register</button>
    </form>
</body>
</html>