<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
</head>
<body>
    <h1>List of Employees</h1>
    <c:choose>
    <c:when test="${empty employees}">
        <p>No employees found.</p>
    </c:when>
    <c:otherwise>
        <table border="1">
            <tr>
                <th>Employee ID</th>
                <th>Name</th>
                <th>Salary Level</th>
                <th>Department ID</th>
                <th>Created By</th>
            </tr>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.eid}</td>
                    <td>${employee.ename}</td>
                    <td>${employee.salaryLevel}</td>
                    <td>${employee.did}</td>
                    <td>${employee.createdby}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
