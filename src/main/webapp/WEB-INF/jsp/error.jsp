<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 19.04.2018
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <fmt:message var="title" key="race.title"/>
    <title>${title}</title>
</head>
<header>
    <h1>${title} ${param.action}</h1>
    <%@include file="includes/header.jsp" %>
</header>
<body>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name or type: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.errorData.throwable}
</body>
</html>
