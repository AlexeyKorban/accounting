<%--
  Created by IntelliJ IDEA.
  User: Loky
  Date: 14.01.2019
  Time: 8:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="../fragments/headTag.jsp"/>

<body>
<jsp:include page="../fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container text-center">
        <br>
        <h2>${typeMessage}</h2>
        <h3>${message}</h3>
    </div>
</div>
<!--
<c:forEach items="${exception.stackTrace}" var="stackTrace">
    ${stackTrace}
</c:forEach>
-->
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
