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
<script type="text/javascript" src="resources/js/accounting.common.js" defer></script>
<script type="text/javascript" src="resources/js/accounting.projects.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
+
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="project.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-3">
                            <label for="startDate"><spring:message code="project.startDate"/></label>
                            <input class="form-control" type="date" name="startDate" id="startDate">
                        </div>
                        <div class="col-3">
                            <label for="endDate"><spring:message code="project.endDate"/></label>
                            <input class="form-control" type="date" name="endDate" id="endDate">
                        </div>
                        <div class="offset-2 col-2">
                            <label for="startTime"><spring:message code="project.startTime"/></label>
                            <input class="form-control" type="time" name="startTime" id="startTime">
                        </div>
                        <div class="col-2">
                            <label for="endTime"><spring:message code="project.endTime"/></label>
                            <input class="form-control" type="time" name="endTime" id="endTime">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-primary" onclick="updateFilteredTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="project.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
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
                <jsp:useBean id="project" type="ru.ldwx.accounting.to.ProjectTo"/>
                <tr data-projectExcess="${project.excess}">
                    <td>
                            ${project.dateTime.toLocalDate()} ${project.dateTime.toLocalTime()}
                    </td>
                    <td>${project.description}</td>
                    <td>${project.sum}</td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a onclick="deleteRow(${project.id})"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"><spring:message code="project.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="project.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="project.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="project.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="project.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="sum" class="col-form-label"><spring:message code="project.sum"/></label>
                        <input type="number" class="form-control" id="sum" name="sum" placeholder="1000">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>