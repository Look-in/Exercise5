<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26.02.2018
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="../resources/global.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="../resources/item.css"/>" rel="stylesheet" type="text/css"/>
<%--ink href="${globalCSS}" rel="stylesheet" />-->
<%--<link href="/resourses/global.css" rel="stylesheet" type="text/css">
<link href="/resourses/item.css" rel="stylesheet" type="text/css">--%>
<html>
<head>
    <fmt:message var="title" key="race.title"/>
    <title>${title}</title>
</head>
<body>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
<%--@elvariable id="user" type="by.service.UserService"--%>
<div class="sort-item">
    <div class="add-item">
        <form name="selecting" method="get">
            <select name="sortingBy"  onchange="this.form.submit()">
           <%--     <option value="" selected>Sort by</option> -->
                <%--@elvariable id="sortBy" type="java.util.List"--%>
                <c:forEach var="sort" items="${sortBy}">
                    <option value="${sort}" ${param.sortingBy == sort ? 'selected="selected"' : ''}>${sort.displayName}</option>
                </c:forEach>
            </select>
        </form>
    </div>
    <div class="add-item" ${user.role.role == 'bookmaker' ? '' : 'hidden="true"' }>
        <c:url var="addurl" value="/modify-race">
        </c:url>
        <a href="${addurl}"><fmt:message key="button.add"/></a>
    </div>
</div>
${message}<br>
<div class="parent-item">
    <%--@elvariable id="races" type="java.util.List"--%>
    <c:forEach var="elem" items="${races}" varStatus="status">
        <div class="img-responsive">
            <img class="item-image" src="${pageContext.request.contextPath}/resources/images/no-img.png">
            <span class="range-txt position-rage-bottom">
    <strong><c:out value="${ elem.race }"/></strong>
    </span>
            <c:url var="editurl" value="/modify-race">
                <c:param name="id" value="${elem.id}"/>
            </c:url>
            <c:url var="showurl" value="/place-rate">
                <c:param name="id" value="${elem.id}"/>
            </c:url>
            <a class="item edit" ${user.role.role == 'client' or user == null ? 'hidden="true"' : '' } href="${editurl}"><fmt:message
                    key="button.edit"/></a>
            <a class="item edit" ${user.role.role == 'client' ? '' : 'hidden="true"' } href="${showurl}"><fmt:message
                    key="race.show"/></a>
            <div class="item delete" ${user.role.role == 'bookmaker' ? '' : 'hidden="true"'}>
                <form name="Delete" action="<c:url value="/modify-race"/>" method="POST">
                    <input type="hidden" name="id" value="${ elem.id }">
                    <input type="hidden" name="action" value="delete">
                    <input type="submit" name="button" value=<fmt:message key="button.delete"/>>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
