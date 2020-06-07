<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mihail_Sazonov
  Date: 07.06.2020
  Time: 2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>new Meal</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<c:out value="${meal.id}"/>
<form action="meals" method="post">
    <input type="hidden" name="uuid" value="${meal.id}">
    calories<input type="text" name="calories" value="${meal.calories}"/>
    <br>date<input type="datetime-localte" name="date" value="${meal.dateTime}"/>
    <br>description<input type="text" name="description" value="${meal.description}"/>
    <br><input type="submit" value="Submit"/>
</form>

</body>
</html>
