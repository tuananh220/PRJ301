<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Tạo Lịch Cho Sản Phẩm</title>
    <style>
       body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
    color: #333;
    margin: 0;
    padding: 20px;
}

h1 {
    font-size: 28px;
    color: #333;
    margin-bottom: 20px;
}

h2 {
    font-size: 22px;
    color: #555;
    margin-top: 10px;
    margin-bottom: 15px;
}

.schedule-table {
    width: 100%;
    border-collapse: collapse;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 30px;
}

.schedule-table th,
.schedule-table td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: center;
}

.schedule-table th {
    background-color: #007BFF;
    color: white;
    font-weight: bold;
}

.schedule-table tr:nth-child(even) {
    background-color: #f2f2f2;
}

.schedule-table tr:hover {
    background-color: #e6f7ff;
}

.input-cell {
    width: 60px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    text-align: center;
}

.create-button {
    padding: 12px 20px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease;
}

.create-button:hover {
    background-color: #0056b3;
}

.create-button:active {
    background-color: #003d80;
}

button {
    padding: 8px 15px;
    margin: 5px;
    border: none;
    border-radius: 4px;
    background-color: #28a745;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #218838;
}

button:active {
    background-color: #1e7e34;
}

p {
    font-size: 18px;
    color: #666;
}

.product-header {
    background-color: #f8f9fa;
    font-weight: bold;
    text-align: left;
    padding-left: 15px;
}

    </style>
</head>
<body>
    <h1>Tạo Lịch Cho Sản Phẩm</h1>
            <h2>Create Schedule Campaign for Plan ID: ${plan.plid}</h2>

    <form action="<c:url value='${pageContext.request.contextPath}/createschedule' />" method="post">
        <input type="hidden" name="planId" value="${plan.plid}" />
                    <h2>Create Schedule Campaign for Plan ID: ${plan.plid}</h2>

        <c:if test="${empty products}">
    <p>Không có sản phẩm nào được tìm thấy!</p>
</c:if>
<c:if test="${empty dates}">
    <p>Không có ngày nào được tìm thấy!</p>
</c:if>

        <table class="schedule-table">
            <thead>
                <tr>
                    <th rowspan="2" class="product-header">Product</th>
                    <c:forEach var="date" items="${dates}">
                        <th colspan="3">${date}</th>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach var="date" items="${dates}">
                        <th>K1</th>
                        <th>K2</th>
                        <th>K3</th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td class="product-header">${product.name}</td>
                        <c:forEach var="date" items="${dates}">
                            <td><input type="text" name="quantity_${product.id}_K1_${date}" class="input-cell" placeholder="K1" /></td>
                            <td><input type="text" name="quantity_${product.id}_K2_${date}" class="input-cell" placeholder="K2" /></td>
                            <td><input type="text" name="quantity_${product.id}_K3_${date}" class="input-cell" placeholder="K3" /></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit" class="create-button">Create Schedule Campaign</button>
    </form>
</body>
</html>
