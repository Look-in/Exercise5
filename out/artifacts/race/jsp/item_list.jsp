<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26.02.2018
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="css/global.css" rel="stylesheet" type="text/css">
<link href="css/item.css" rel="stylesheet" type="text/css">
<link href="${globalCSS}" rel="stylesheet" />
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
        <strong><c:out value="${ elem.name }"/></strong>
  </span>
                <c:url var="editurl" value="/viewitemmodify">
                    <c:param name="action" value="EDIT"/>
                    <c:param name="itemId" value="${elem.raceId}"/>
                </c:url>
                <a class="item edit" ${param.itemType == "ALL" ? 'hidden="true"' : ''} href="${editurl}">Edit</a>
                <c:url var="deleteurl" value="/viewitemmodify">
                    <c:param name="action" value="DELETE"/>
                    <c:param name="itemId" value="${elem.raceId}"/>
                </c:url>
                <a class="item delete" ${param.itemType == "ALL" ? 'hidden="true"' : ''} href="${deleteurl}"
                   title="">Delete</a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
