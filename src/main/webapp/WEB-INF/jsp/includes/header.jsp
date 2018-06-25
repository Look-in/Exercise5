<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 27.02.2018
  Time: 17:42
--%>
<script type="text/javascript">
var notice = new PNotify({
text: $('#form_notice').html(),
icon: false,
width: 'auto',
hide: false,
buttons: {
closer: false,
sticker: false
},
insert_brs: false
});
notice.get().find('form.pf-form').on('click', '[name=cancel]', function() {
notice.remove();
}).submit(function() {
var username = $(this).find('input[name=username]').val();
if (!username) {
alert('Please provide a username.');
return false;
}
notice.update({
title: 'Welcome',
text: 'Successfully logged in as ' + username,
icon: true,
width: PNotify.prototype.options.width,
hide: true,
buttons: {
closer: true,
sticker: true
},
type: 'success'
});
return false;
});
</script>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <div>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" id="logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <input class="user-form logout" type="submit" name="submit" value="Log Out">
        </form>
    </div>
    <div>
        <sec:authorize access="hasRole('USER')">
            <c:url var="userRates" value="/place-rate"/>
        </sec:authorize>
        <a class="user-form" href="${userRates}"> Cart ${countUserRates}</a>
    </div>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == null}">
    <c:url var="url" value="login"></c:url>
    <div>
        <a class="user-form" href="${url}" title="">Login</a>
    </div>
    <div>
        <a class="user-form" href=""> Anonymous </a>
    </div>
</c:if>
<div>
    <sec:authorize access="hasRole('USER')">
        <c:set var="ordercart" value="/shopping-cart/show-orders"/>
    </sec:authorize>
    <a class="user-form" href="${ordercart}">Account</a>
</div>


