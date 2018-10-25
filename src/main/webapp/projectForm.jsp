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
<head>
    <title>Project</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>${param.action == 'create' ? 'Create project' : 'Edit project'}</h2>
    <hr>
    <jsp:useBean id="project" type="local.ldwx.accounting.model.Project" scope="request"/>
    <form method="post" action="projects">
        <input type="hidden" name="id" value="${project.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${project.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${project.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Sum:</dt>
            <dd><input type="number" value="${project.sum}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
