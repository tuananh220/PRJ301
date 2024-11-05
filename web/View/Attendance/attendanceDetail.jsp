<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags/tags.tld" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Attendance Details at workshop no 1 - ${param.date} - ${param.shift}</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 20px;
            }
            h1 {
                color: #333;
                text-align: center;
                margin-bottom: 20px;
            }
            form {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-bottom: 20px;
            }
            label {
                font-weight: bold;
            }
            select, button {
                padding: 5px;
                font-size: 1rem;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 15px;
                text-align: left;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            input[type="text"], input[type="number"] {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
            }
            button {
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <h1>Attendance Details at workshop no 1</h1>

        <!-- Filter Options -->
        <form method="get" action="/attendancedetail">
            <!-- Filter Options -->
            <label for="date">Select Date:</label>
            <select name="date" id="date">
                <c:forEach var="campaign" items="${scheduleCampaigns}">
                    <option value="${campaign.date}" ${campaign.date == param.date ? "selected" : ""}>
                        <mytag:ToVietnameseDate value="${campaign.date}" />
                    </option>
                </c:forEach>
            </select>

            <label for="shift">Select Shift:</label>
            <select name="shift" id="shift">
                <option value="K1" ${"K1" == param.shift ? "selected" : ""}>K1</option>
                <option value="K2" ${"K2" == param.shift ? "selected" : ""}>K2</option>
                <option value="K3" ${"K3" == param.shift ? "selected" : ""}>K3</option>
            </select>

            <button type="submit">Take Attendance</button>
        </form>

        <!-- Attendance Details Table -->
        <form method="post" action="/attendancedetail/save">
            <table>
                <thead>
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
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${attendances}">
                        <tr>
                            <td>${attendance.workerSchedule.employee.eid}</td>
                            <td>${attendance.workerSchedule.employee.ename}</td>
                            <td>${attendance.product.id}</td>
                            <td>${attendance.product.name}</td>
                            <td>${attendance.workerSchedule.quantity}</td>
                            <td>
                                <input type="number" name="actualQuantity_${attendance.aid}" >
                            </td>
                            <td>${attendance.alpha}</td>
                            <td>
                                <input type="text" name="note_${attendance.aid}" value="${attendance.note}">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        </form>
    </body>
</html>
