<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 25.02.2018
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="css/global.css" rel="stylesheet" type="text/css">
<link href="css/item.css" rel="stylesheet" type="text/css">
<html>
<%@include file="jsp/includes/header.jsp" %>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <div>
  <c:url var="selectitem" value="view-race">
    <c:param name="action" value="list"/>
    <c:param name="itemType" value="ALL"/>
  </c:url>
  <a href="${selectitem}" title="">ItemList</a>
  </div>
   </body>
</html>
