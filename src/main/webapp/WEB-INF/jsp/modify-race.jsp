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
    <link href="<c:url value="../resources/animate.css"/>" rel="stylesheet" type="text/css"/>
    <fmt:message var="title" key="race.modify.title"/>
    <title>${title}</title>
    <script src="http://code.jquery.com/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/pnotify.custom.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/pnotify.custom.min.css" media="all" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        $(document).ready(function () {
            $(".rateResult").change(
                function () {
                    var $this = $(this);
                    var selected = $this.val();
                    var rateId = $(this).closest("div").find("input[name=rateId]").val();
                    $.ajax({
                        type: "POST",
                        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        scriptCharset: "utf-8",
                        url: "/modify-rate/change-rateResult",
                        data: {
                            'rateId': rateId,
                            'rateResult': selected
                        },
                        success: function(response) {
                            new PNotify({
                                title: 'Сохранено',
                                text: response,
                                delay: 2000,
                                type: 'success',
                                animate: {
                                    animate: true,
                                    in_class: 'fadeInRight',
                                    out_class: 'fadeOutRight'
                                }
                            });
                        }
                    });// ajax
                });//function
        });//ready
    </script>
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
    <%--@elvariable id="rates" type="java.util.List"--%>
    <c:forEach var="elem" items="${rates}" varStatus="status"><%--@elvariable id="user" type="by.entity.User"--%>
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
            <div class="item delete" ${race.id != null and (pageContext.request.isUserInRole('ADMINISTRATOR') or elem.rateResult.id == 1) ? '' : 'hidden="true"' } >
                <sec:authorize access="hasRole('ADMINISTRATOR')">
                    <div class="divResult">
                        <input type="hidden" name="rateId" value="${  elem.id }">
                        <select name="rateResult" class="rateResult">
                            <%--@elvariable id="rateResults" type="java.util.List"--%>
                            <c:forEach var="result" items="${rateResults}">
                                <option ${result.id == elem.rateResult.id ? 'selected="true"' : ''}
                                        value="${result.id}">${result.rateResult}</option>
                            </c:forEach>
                        </select>
                    </div>
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

