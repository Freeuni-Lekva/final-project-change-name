<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="icon" href="icon.png">
        <link rel="stylesheet" href="style.css">
        <title>StartNewBand</title>
    </head>
<header>
    <%@include  file="nav.html" %>
</header>
<body>
<div class="card">
    <form method="POST" action="/newBand">
        <label>Band name</label>
        <input type="text" name="bandName"/><br><br>
        <button type="submit">Create</button>
    </form>
</div>
</body>
</html>