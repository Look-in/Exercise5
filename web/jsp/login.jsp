<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 19.04.2018
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="css/global.css" rel="stylesheet" type="text/css">
<link href="css/item.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="loginForm" method="POST" action="loginForm">
    <input type="hidden" name="command" value="login" />
    Login:<br/>
    <input type="text" name="username" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${messages}
    <br/>
    <input type="submit" value="Log in"/>
</form><hr/>
</body></html>