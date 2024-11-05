<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Danh Sách Kế Hoạch</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                color: #333;
                line-height: 1.6;
                margin: 20px;
            }
            h1 {
                text-align: center;
                color: #333;
                font-size: 2em;
                margin-bottom: 20px;
            }
            table {
                width: 90%;
                margin: 0 auto;
                border-collapse: collapse;
                box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 15px;
                text-align: center;
            }
            th {
                background-color: #4CAF50;
                color: white;
                font-weight: bold;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            button {
                padding: 8px 15px;
                margin: 5px;
                background-color: #008CBA;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            button:hover {
                background-color: #005f73;
            }
            button:focus {
                outline: none;
            }
            form {
                display: inline;
            }
        </style>
    </head>
    <body>
        <h1>Danh Sách Kế Hoạch</h1>
        <table>
            <thead>
                <tr>
                    <th>Plan ID</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="plan" items="${plans}">
                    <tr>
                        <td>${plan.plid}</td>
                        <td>${plan.startd}</td>
                        <td>${plan.endd}</td>
                        <td>${plan.status}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/schedulecreate" method="get" style="display:inline;">
                                <input type="hidden" name="planId" value="${plan.plid}" />
                                <button type="submit">Create Schedule</button>
                            </form>
                            <form action="detailSchedule" method="get" style="display:inline;">
                                <input type="hidden" name="plid" value="${plan.plid}" />
                                <button type="submit">Detail Schedule</button>
                            </form>
                            <form action="planDetail" method="get" style="display:inline;">
                                <input type="hidden" name="plid" value="${plan.plid}" />
                                <button type="submit">Plan Detail</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
