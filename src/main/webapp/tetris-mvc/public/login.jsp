<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error =
            (String) request.getAttribute("error");
%>

<% if(error != null){ %>

<div class="error-message">
    <%= error %>
</div>

<% } %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Tetris MVC</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/tetris-mvc/css/login.css">
</head>
<body>

<div class="login-container">

    <div class="login-card">

        <h1 class="game-title">TETRIS</h1>
        <p class="subtitle">MVC GAME LOGIN</p>

        <form action="${pageContext.request.contextPath}/login" method="post">

            <div class="input-group">
                <label>Email</label>
                <input type="email"
                       name="email"
                       placeholder="Nhập email"
                       required>
            </div>

            <div class="input-group">
                <label>Mật khẩu</label>
                <input type="password"
                       name="password"
                       placeholder="Nhập mật khẩu"
                       required>
            </div>

            <button type="submit" class="login-btn">
                ĐĂNG NHẬP
            </button>

        </form>

        <div class="bottom-text">
            Chưa có tài khoản?
            <a href="${pageContext.request.contextPath}/tetris-mvc/public/Register.jsp">Đăng ký</a>
        </div>

    </div>

</div>

</body>
</html>