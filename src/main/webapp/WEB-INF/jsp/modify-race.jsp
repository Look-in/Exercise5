<%--@elvariable id="race" type="by.entity.race"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 28.02.2018
  Time: 22:01
  Страница ввода/изменения Race.
--%>
<html>
<head>
    <link href="<c:url value="../resources/global.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="../resources/item.css"/>" rel="stylesheet" type="text/css"/>
    <fmt:message var="title" key="race.modify.title"/>
    <title>${title}</title>
</head>
<body>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
${message}<br>
<form:form method="POST" name="Modify" action="/modify-race" modelAttribute="race" id="race">
    <table class="table-edit">
        <form:input type="hidden" path="id"/>
        <tr>
            <td><form:label path="race">${title}:</form:label></td>
            <td><form:input readonly="${pageContext.request.isUserInRole('BOOKMAKER') ? 'false' : 'true' }"
                            path="race"/></td>
        </tr>
    </table>
    <sec:authorize access="hasRole('BOOKMAKER')">
        <input type="submit" name="button" value=<fmt:message key="button.save"/>>
    </sec:authorize>
    <a href="<c:url value="/view-race"/>" title=""><fmt:message key="button.cancel"/></a>
</form:form>
<br>
<hr>
<br>
<fmt:message var="rateTitle" key="rate.title"/>
<div><strong>${rateTitle}</strong></div>
<div class="add-item" ${pageContext.request.isUserInRole('BOOKMAKER') and race.id != null ? '' : 'hidden="true"'}>
    <c:url var="addurl" value="/modify-rate">
        <c:param name="raceId" value="${race.id}"/>
    </c:url>
    <a href="${addurl}"><fmt:message key="button.add"/></a>
</div>
<div class="parent-item">
    <c:forEach var="elem" items="${rates}" varStatus="status">
        <div class="img-responsive">
            <img class="item-image" src="${pageContext.request.contextPath}/resources/images/no-img.png">
            <c:out value="${ elem.rateResult.rateResult }"/><br>
            <span class="range-txt position-rage-bottom">
                 <strong><c:out value="${ elem.rate }"/></strong>
             </span>
            <c:url var="editurl" value="/modify-rate">
                <c:param name="id" value="${elem.id}"/>
            </c:url>
            <a class="item edit" ${elem.rateResult.id == 1 and user.role.role == 'bookmaker' ? '' : 'hidden="true"' }
               href="${editurl}"><fmt:message
                    key="button.edit"/></a>
            <div class="item delete" ${race.id != null and (user.role.role == 'administrator' or elem.rateResult.id == 1) ? '' : 'hidden="true"' } >
                <sec:authorize access="hasRole('ADMINISTRATOR')">
                    <form action="/modify-rate/change-rateResult" method="POST">
                        <input type="hidden" name="rateId" value="${  elem.id }">
                        <input type="hidden" name="raceId" value="${  race.id }">
                        <select name="rateResult" onchange="this.form.submit()">
                            <c:forEach var="result" items="${rateResults}">
                                <option ${result.id == elem.rateResult.id ? 'selected="true"' : ''}
                                        value="${result.id}">${result.rateResult}</option>
                            </c:forEach>
                        </select>
                    </form>
                </sec:authorize>
                <sec:authorize access="hasRole('BOOKMAKER')">
                    <form name="Delete" action="<c:url value="/modify-rate"/>" method="POST">
                        <input type="hidden" name="id" value="${ elem.id }">
                        <input type="hidden" name="raceId" value="${  race.id }">
                        <input type="hidden" name="action" value="delete">
                        <input type="submit" name="button" value=<fmt:message key="button.delete"/>>
                    </form>
                </sec:authorize>
                <sec:authorize access="hasRole('CLIENT')">
                    <form name="PlaceRate" action="<c:url value="/place-rate"/>" method="POST">
                        <input type="hidden" name="id" value="${ elem.id }">
                        <input type="submit" name="button" value=<fmt:message key="button.place-rate"/>>
                    </form>
                </sec:authorize>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

