<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>List of Employees</h1>
    <h2>Department: ${requestScope.department.name}</h2>
    <c:if test="${not empty employees}">
        <table>
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Full Name</th>
                    <th>Salary Level</th>
                    <th>Hourly Rate</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${requestScope.employees}">
                    <tr>
                        <td>${employee.eid}</td>
                        <td>${employee.ename}</td>
                        <td>${employee.salaryLevel}</td>
                        <td>${employee.hourlyRate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty employees}">
        <p>No employees found.</p>
    </c:if>
</body>
</html>