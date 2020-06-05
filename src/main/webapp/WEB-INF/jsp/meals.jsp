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
    <style>
        table {
            border: 1px solid grey;
        }

        th {
            border: 1px solid grey;
            background-color: darkgray;
        }

        td {
            border: 1px solid grey;
            background-color: gainsboro
        }
    </style>
</head>
<body>
Test meal body
<table>
    <tr>
        <th>date</th>
        <th>time</th>
        <th>description</th>
        <th>calories</th>
        <th>excess</th>
    </tr> <!--ряд с ячейками заголовков-->

    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
    <c:forEach var="element" items="${meals}">
        <jsp:useBean id="element" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <td>${element.dateTime.toLocalDate()}</td>
            <td>${element.dateTime.toLocalTime()}</td>
            <td>${element.description}</td>
            <td>${element.calories}</td>
            <c:choose>
                <c:when test="${element.excess == 'true'}">
                    <td style="background-color: greenyellow">${element.excess}</td>
                </c:when>
                <c:otherwise>
                    <td style="background-color: red">${element.excess}</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>

</body>
</html>
