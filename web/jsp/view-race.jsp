<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26.02.2018
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="css/global.css" rel="stylesheet" type="text/css">
<link href="css/item.css" rel="stylesheet" type="text/css">
<%--ink href="${globalCSS}" rel="stylesheet" />-->
<%--<link href="/resourses/global.css" rel="stylesheet" type="text/css">
<link href="/resourses/item.css" rel="stylesheet" type="text/css">--%>
<html>
<head>
    <title>${param.type}</title>
</head>
<body>
<%@include file="includes/header.jsp" %>
<div>
    <div class="sort-item">
        <div class="add-item">
            <form name="selecting" method="get">
                <input type="hidden" name="action" value="list">
                <input type="hidden" name="itemType" value="${ param.itemType }">
                <select name="sortingBy">
                    <option value="" selected>Sort by</option>
                    <c:forEach var="sort" items="${sortBy}">
                        <option value="${sort}" ${param.sortingBy == sort ? 'selected="selected"' : ''}>${sort.displayName}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Sort"/>
            </form>
        </div>
        <div class="add-item">
            <c:url var="addurl" value="/viewitemmodify">
                <c:param name="action" value="ADD"/>
                <c:param name="itemType" value="${ param.itemType }"/>
            </c:url>
            <a ${param.itemType == "ALL" ? 'hidden="true"' : ''} href="${addurl}">Add item</a>
        </div>
    </div>
    <div>
        <c:url var="selectitem" value="selectitemservlet">
            <c:param name="action" value="list"/>
            <c:param name="itemType" value="ALL"/>
        </c:url>
        <a href="${selectitem}" title="">ALL</a>
        <c:forEach var="type" items="${itemType}">
            <c:url var="selectitem" value="selectitemservlet">
                <c:param name="action" value="list"/>
                <c:param name="itemType" value="${type.itemTypeId}"/>
                <c:param name="type" value="${type.itemType}"/>
            </c:url>
            <a href="${selectitem}" title="">${type.itemType}</a>
        </c:forEach>
    </div>

    <div class="parent-item">
        <c:forEach var="elem" items="${race}" varStatus="status">
            <div class="img-responsive">
                <img class="item-image" src="css/images/no-img.png">
                <span class="range-txt position-rage-bottom">
    <strong><c:out value="${ elem.race }"/></strong>
    </span>
                <c:url var="editurl" value="/modify-race">
                    <c:param name="action" value="EDIT"/>
                    <c:param name="id" value="${elem.id}"/>
                </c:url>
                <a class="item edit" ${user.role.role == 'bookmaker' ? '' : 'hidden="true"' } href="${editurl}">Edit</a>
                <div class="item delete" ${user.role.role == 'bookmaker' ? '' : 'hidden="true"'}>
                    <form name="Delete" action="/modify-race" method="POST">
                        <input type="hidden" name="id" value="${ elem.id }">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="submit" name="button" value="DELETE"/>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
