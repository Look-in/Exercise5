<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%@include file="includes/defaultitemmodify.jsp" %>
<tr>
    <td>
        Fork:
    </td>
    <td>
        <input type="text" id="ufork" name="fork"
               size="30" value="${ item.fork }">
    </td>
</tr>
<tr>
    <td>
        Brakes:
    </td>
    <td>
        <input type="text" id="ubrakes" name="brakes"
               size="30" value="${ item.brakes }">
    </td>
</tr>
<tr>
    <td>
        Frame:
    </td>
    <td>
        <input type="text" id="uframe" name="frame"
               size="30" value="${ item.frame }">
    </td>
</tr>
</table>
<input type="submit" name="button" value="${param.action}"/>
<a href="selectitemservlet" title="">Cancel</a>
</form>
</body>
</html>
