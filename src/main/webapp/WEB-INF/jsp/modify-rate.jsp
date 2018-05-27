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
    <fmt:message var="title" key="race.title"/>
    <title>${title}</title>
    <link href="<c:url value="../resources/global.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="../resources/item.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
<form name="Modify" action="<c:url value="/modify-rate"/>" method="POST">
    <input type="hidden" name="id" value="${  rate.id }">
    <input type="hidden" name="rateResult.id" value="${  rate.rateResult.id }">
    <input type="hidden" name="rateResult.rateResult" value="${  rate.rateResult.rateResult }">
    <input type="hidden" name="race.id" value="${  rate.race.id }">
    <input type="hidden" name="race.race" value="${  rate.race.race }">
    <table class="table-edit">
        <tr>
            <td><label for="rate"><fmt:message key="race.title"/>:</label></td>
            <td>
                <input type="text" id="rate" name="rate"
                       size="30" value="${ rate.rate }">
            </td>
        </tr>
    </table>
    <input type="submit" name="button" value=<fmt:message key="button.save"/>>
    <c:url var="cancelUrl" value="/modify-race">
        <c:param name="id" value="${rate.race.id}"/>
    </c:url>
    <a href="${cancelUrl}" title=""><fmt:message key="button.cancel"/></a>
</form>
</body>
</html>