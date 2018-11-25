<%--
  Created by IntelliJ IDEA.
  User: Loky
  Date: 18.10.2018
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="project.title"/></h3>

    <form method="post" action="projects/filter">
        <dl>
            <dt><spring:message code="project.startDate"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="project.endDate"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="project.startTime"/>:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="project.endTime"/>:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="project.filter"/></button>
    </form>
    <hr>
    <a href="projects/create"><spring:message code="project.add"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="project.dateTime"/></th>
            <th><spring:message code="project.description"/></th>
            <th><spring:message code="project.sum"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${projects}" var="project">
            <jsp:useBean id="project" scope="page" type="ru.ldwx.accounting.to.ProjectTo"/>
            <tr data-projectExcess="${project.excess}">
                <td>
                        ${project.dateTime.toLocalDate()} ${project.dateTime.toLocalTime()}
<%--                        <%=TimeUtil.toString(project.getDateTime())%>--%>
                        <%--${fn:replace(project.dateTime, 'T', ' ')}--%>
<%--                        ${fn:formatDateTime(project.dateTime)}--%>
                </td>
                <td>${project.description}</td>
                <td>${project.sum}</td>
                <td><a href="projects/update?id=${project.id}"><spring:message code="common.update"/></a></td>
                <td><a href="projects/delete?id=${project.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
