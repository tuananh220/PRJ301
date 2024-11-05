<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance at Workshop</title>
</head>
<body>
    <h1>Attendance at Workshop</h1>
    <form method="post" action="attendance">
        <table border="1">
            <tr>
                <th>Employee ID</th>
                <th>Full Name</th>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Ordered Quantity</th>
                <th>Actual Quantity</th>
                <th>Alpha</th>
                <th>Note</th>
            </tr>
            <c:forEach var="workerSchedule" items="${workerSchedules}">
                <tr>
                    <td>${workerSchedule.employee.eid}</td>
                    <td>${workerSchedule.employee.ename}</td>
                    <td>${workerSchedule.scheduleCampain.planCampain.product.id}</td>
                    <td>${workerSchedule.scheduleCampain.planCampain.product.name}</td>
                    <td>${workerSchedule.quantity}</td>
                    <td><input type="text" name="actualQuantity" value="${workerSchedule.quantity}"></td>
                    <td>${workerSchedule.scheduleCampain.quantity}</td>
                    <td>
                        <input type="hidden" name="wsid" value="${workerSchedule.wsid}">
                        <input type="text" name="note" value="${workerSchedule.attendance.note}">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit">Save Notes</button>
    </form>
</body>
</html>
