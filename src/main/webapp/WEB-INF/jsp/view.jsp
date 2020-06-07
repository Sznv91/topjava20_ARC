<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Mihail_Sazonov
  Date: 07.06.2020
  Time: 3:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View CRUD Meal</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
View Page
<br> <a href="./meals?action=add">new Meal</a>
<table>
    <tr>
        <th>id</th>
        <th>date</th>
        <th>time</th>
        <th>description</th>
        <th>calories</th>
        <th>action</th>
    </tr>
    <jsp:useBean id="storage" type="java.util.List<ru.javawebinar.topjava.model.Meal>" scope="request"/>
    <c:forEach var="meal" items="${storage}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal"/>
        <tr>
            <td>${meal.id}</td>
            <td>${meal.dateTime.toLocalDate()}</td>
            <td>${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="./meals?action=delete&id=${meal.id}"> delete </a>
                <a href="./meals?action=update&id=${meal.id}"> edit </a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
