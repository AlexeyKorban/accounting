<%--
  Created by IntelliJ IDEA.
  User: Loky
  Date: 18.10.2018
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <jsp:useBean id="project" type="local.ldwx.accounting.model.Project" scope="request"/>
    <h3><spring:message code="${project.isNew() ? 'project.add' : 'project.edit'}"/></h3>
    <hr>
    <form method="post" action="projects">
        <input type="hidden" name="id" value="${project.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${project.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><input type="text" value="${project.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="project.sum"/>:</dt>
            <dd><input type="number" value="${project.sum}" name="sum" required></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
