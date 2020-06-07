<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View CRUD Meal</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
View Page
<br><a href="./">Go home</a>
<br> <a href="./meals?action=add">new Meal</a> | <a href="./meals?action=fillPredefined">fill predefined meal</a>
<br>Calorie limit:
<form name="calories_limit" method="get" action="meals">
    <input type="number" name="limit" value="${limit}"/>
    <input type="button" name="bt" value="submit"
           onclick="{document.calories_limit.value=limit.value; document.calories_limit.submit();}">
</form>
<table>
    <tr>
        <th>date</th>
        <th>description</th>
        <th>calories</th>
        <th>action</th>
    </tr>
    <jsp:useBean id="storage" type="java.util.List<ru.javawebinar.topjava.model.MealTo>" scope="request"/>
    <c:forEach var="meal" items="${storage}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:
            <c:out value="${meal.excess ? 'red' : 'green'}"/>">
            <td><%=meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))%>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="./meals?action=delete&id=${meal.id}"> delete </a>
                <a href="./meals?action=update&id=${meal.id}"> edit </a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
