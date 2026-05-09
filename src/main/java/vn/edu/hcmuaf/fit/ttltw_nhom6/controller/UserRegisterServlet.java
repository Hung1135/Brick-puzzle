package vn.edu.hcmuaf.fit.ttltw_nhom6.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.ttltw_nhom6.dao.UserDAO;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.User;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate dữ liệu
        if (username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/tetris-mvc/public/Register.jsp")
                    .forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu xác nhận
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/tetris-mvc/public/Register.jsp")
                    .forward(request, response);
            return;
        }
        // Kiểm tra email tồn tại
        if (userDAO.emailExists(email)) {
            request.setAttribute("error", "Email đã tồn tại!");
            request.getRequestDispatcher("/tetris-mvc/public/Register.jsp")
                    .forward(request, response);
            return;
        }

        // Hash password
        String passwordHash = hashPassword(password);

        // Tạo user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);

        // Đăng ký
        boolean success = userDAO.register(user);

        if (success) {
            request.setAttribute("success", "Đăng ký thành công!");
            request.getRequestDispatcher("/tetris-mvc/public/login.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký thất bại!");
            request.getRequestDispatcher("/tetris-mvc/public/Register.jsp")
                    .forward(request, response);
        }
    }

    // Hàm hash SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}