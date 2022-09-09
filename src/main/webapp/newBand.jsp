<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>StartNewBand</title>
    </head>
<body>
    <%@include  file="nav.html" %>
    <form method="POST" action="/newBand">
        <label>Band name</label>
        <input type="text" name="bandName"/><br/>
        <button type="submit">Create</button>
    </form>
</body>
</html>