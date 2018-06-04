<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 02.04.2018
  Time: 9:30
--%>
<html>
<head>
    <title>Access Denied</title>
</head>
<body>
<h1>You do not have permission to access this page!
</h1>
<form action="/logout" method="post">
    <input type="submit" value="Sign in as different user" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>