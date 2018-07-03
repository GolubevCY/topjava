<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions"  %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals:</h2>
    <table border="1">
        <!-- here should go some titles... -->
        <tr>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=2>Actions</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <tr style="color: ${meal.exceed ? "#ff0000" : "#008000"};">
                <td>
                    <c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy.MM.dd hh:mm')}" />
                </td>
                <td>
                    <c:out value="${meal.description}" />
                </td>
                <td>
                    <c:out value="${meal.calories}" />
                </td>
                <td>
                    <a href="listMeal?action=edit&mealId=<c:out value="${meal.id}"/>">Edit</a>
                </td>
                <td>
                    <a href="listMeal?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
<p><a href="listMeal?action=insert">Add meal</a></p>
</body>
</html>