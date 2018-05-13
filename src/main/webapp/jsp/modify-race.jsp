<%--@elvariable id="race" type="by.entity.race"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 28.02.2018
  Time: 22:01
  Страница ввода/изменения Race.
--%>
<html>
<head>
    <link href="css/global.css" rel="stylesheet" type="text/css">
    <link href="css/item.css" rel="stylesheet" type="text/css">
    <fmt:message var="title" key="race.modify.title"/>
    <title>${title}</title>
</head>
<body>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
${message}<br>
<form name="Modify" action="<c:url value="/modify-race"/>" method="POST">
    <table class="table-edit">
        <input type="hidden" name="id" value="${  race.id }">
        <tr>
            <td><label for="race">${title}:</label></td>
            <td>
                <input type="text" id="race" name="race"
                       size="30" value="${ race.race }">
            </td>
        </tr>
    </table>
    <input type="submit" name="button" value=<fmt:message key="button.save"/>>
    <a href="<c:url value="/view-race"/>" title=""><fmt:message key="button.cancel"/></a>
</form>
<br>
<hr>
<br>
<fmt:message var="rateTitle" key="rate.title"/>
<div><strong>${rateTitle}</strong></div>
<div class="add-item">
    <c:url var="addurl" value="/modify-rate">
        <c:param name="raceId" value="${race.id}"/>
    </c:url>
    <a href="${addurl}"><fmt:message key="button.add"/></a>
</div>
<div class="parent-item">
    <c:forEach var="elem" items="${rates}" varStatus="status">
        <div class="img-responsive">
            <img class="item-image" src="css/images/no-img.png">
            <c:out value="${ elem.rateResult.rateResult }"/><br>
            <span class="range-txt position-rage-bottom">
                 <strong><c:out value="${ elem.rate }"/></strong>
             </span>
            <c:url var="editurl" value="/modify-rate">
                <c:param name="id" value="${elem.id}"/>
            </c:url>
            <a class="item edit" ${elem.rateResult.id == 1 ? '' : 'hidden="true"' } href="${editurl}"><fmt:message
                    key="button.edit"/></a>
            <div class="item delete" ${elem.rateResult.id == 1 ? '' : 'hidden="true"' } >
                <form name="Delete" action="<c:url value="/modify-rate"/>" method="POST">
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

