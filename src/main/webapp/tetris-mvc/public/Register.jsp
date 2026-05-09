<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");

    String username = request.getParameter("username") != null
            ? request.getParameter("username")
            : "";

    String email = request.getParameter("email") != null
            ? request.getParameter("email")
            : "";
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">

    <title>Đăng ký - Tetris MVC</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/tetris-mvc/css/register.css">
</head>

<body>

<div class="login-container">

    <div class="login-card">

        <h1 class="game-title">TETRIS</h1>
        <p class="subtitle">MVC GAME REGISTER</p>

        <!-- MESSAGE -->
        <% if (error != null) { %>
        <div class="error-message">
            <%= error %>
        </div>
        <% } %>

        <% if (success != null) { %>
        <div class="success-message">
            <%= success %>
        </div>
        <% } %>

        <!-- FORM -->
        <form action="${pageContext.request.contextPath}/register"
              method="post">

            <!-- USERNAME -->
            <div class="input-group">

                <label>Tên người dùng</label>

                <input type="text"
                       name="username"
                       value="<%= username %>"
                       placeholder="Nhập tên người dùng"
                       required>

            </div>

            <!-- EMAIL -->
            <div class="input-group">

                <label>Email</label>

                <input type="email"
                       name="email"
                       value="<%= email %>"
                       placeholder="Nhập email"
                       required>

            </div>

            <!-- PASSWORD -->
            <div class="input-group">

                <label>Mật khẩu</label>

                <input type="password"
                       name="password"
                       placeholder="Nhập mật khẩu"
                       required>

            </div>

            <!-- CONFIRM PASSWORD -->
            <div class="input-group">

                <label>Xác nhận mật khẩu</label>

                <input type="password"
                       name="confirmPassword"
                       placeholder="Xác nhận mật khẩu"
                       required>

            </div>

            <!-- BUTTON -->
            <button type="submit" class="login-btn">
                ĐĂNG KÝ
            </button>

        </form>

        <!-- LOGIN -->
        <div class="bottom-text">

            Đã có tài khoản?

            <a href="${pageContext.request.contextPath}/tetris-mvc/public/login.jsp">
                Đăng Nhập
            </a>

        </div>

    </div>

</div>

</body>
</html>