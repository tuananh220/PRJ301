<%@ page import="java.util.List" %>
<%@ page import="model.accesscontrol.Features" %>
<%@ page import="model.accesscontrol.User" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giám Đốc - Quản Lý Quyền Hạn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #4a90e2;
        }
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
    </style>
</head>
<script>
        function redirectToPage() {
            var selectedValue = document.getElementById("functionSelect").value;
            if (selectedValue !== "#") {
                window.location.href = selectedValue;
            }
        }
    </script>
<body>
    <div class="container">
        <h2>Chức Năng Quản Lý Của Giám Đốc</h2>
        <select id="functionSelect">
            <option value="#">Chọn một chức năng</option>
            <option value="<%= request.getContextPath() %>/employees/list">Quản Lý Nhân Viên</option>
            <option value="<%= request.getContextPath() %>/product/list">Quản Lý Sản Phẩm</option>
            <option value="<%= request.getContextPath() %>/productionplan/create">Quản Lý Kế Hoạch</option>
            <option value="<%= request.getContextPath() %>/report/list">Quản Lý Báo Cáo</option>
        </select>
        <button class="btn-confirm" onclick="redirectToPage()">Xác Nhận</button>
    </div>
    
</body>
</html>
