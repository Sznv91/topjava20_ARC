<%--
  Created by IntelliJ IDEA.
  User: Sazonov
  Date: 05.06.2020
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals Page</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
Meal page
<table>
    <tr>
        <th>date</th>
        <th>time</th>
        <th>description</th>
        <th>calories</th>
        <th>excess</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
    <c:forEach var="element" items="${meals}">
        <jsp:useBean id="element" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <td>${element.dateTime.toLocalDate()}</td>
            <td>${element.dateTime.toLocalTime()}</td>
            <td>${element.description}</td>
            <td>${element.calories}</td>
            <td style="background-color:
                <c:out value="${element.excess ? 'greenyellow' : 'red'}"/>
                    ">${element.excess}</td>
        </tr>
    </c:forEach>
</table>
<br>
<input type="button" value="Back" onclick="history.go(-1)">
</body>
</html>
