<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 28.02.2018
  Time: 22:01
  Страница ввода/изменения Clothes.
--%>
<link href="css/global.css" rel="stylesheet" type="text/css">
<link href="css/item.css" rel="stylesheet" type="text/css">
<head>
    <title>${param.type} ${param.action}</title>
</head>
<%@include file="includes/header.jsp" %>
<form name="Modify" action="/modify-race" method="POST">
    <table class="table-edit">
        <tr>
            <td>
                Race:
            </td>
            <td>
                <input type="text" id="race" name="race"
                       size="30" value="${ elem.race }">
            </td>
        </tr>
        <input type="hidden" name="id" value="${ elem.id }">
    </table>
    <input type="submit" name="button" value="Сохранить"/>
    <a href="/view-race" title="">Cancel</a>
</form>
</body>
</html>
