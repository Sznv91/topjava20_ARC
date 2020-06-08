<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <title>${action} Meal</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
<c:out value="Operation: ${action}"/>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="hidden" name="action" value="${action}">
    calories<input type="number" name="calories" value="${meal.calories}"/>
    <br>date<input type="datetime-local" name="date" value="${meal.dateTime}"/>
    <br>description<input type="text" name="description" value="${meal.description}"/>
    <br><input type="submit" value="Submit"/>
</form>

</body>
</html>
