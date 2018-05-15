<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 19.04.2018
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:message var="title" key="login.title"/>
    <title>${title}</title>
    <link href="css/global.css" rel="stylesheet" type="text/css">
    <link href="css/item.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
<form name="loginForm" method="POST" action="loginForm">
    <input type="hidden" name="command" value="login" />
    <fmt:message key="login.login"/><br/>
    <input type="text" name="username" value=""/>
    <br/><fmt:message key="login.password"/><br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${messages}
    <br/>
    <input type="submit" value=<fmt:message key="login"/>>
</form><hr/>
</body>
</html>