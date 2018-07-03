<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions"  %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>

<form method="POST" action='listMeal' name="frmAddUser">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.id}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Date/Time : <input
        type="text" name="dateTime"
        value="<c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy.MM.dd hh:mm')}" />" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>