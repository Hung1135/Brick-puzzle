package vn.edu.hcmuaf.fit.ttltw_nhom6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.edu.hcmuaf.fit.ttltw_nhom6.dao.UserDAO;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.User;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/tetris-mvc/public/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        // Validate
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Vui lòng nhập đầy đủ thông tin!"
            );

            request.getRequestDispatcher(
                    "/tetris-mvc/public/login.jsp"
            ).forward(request, response);

            return;
        }

        // Hash password
        String passwordHash =
                hashPassword(password);

        // Login
        Optional<User> optionalUser =
                userDAO.login(email, passwordHash);

        // Thành công
        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            // Tạo session
            HttpSession session =
                    request.getSession();

            // Lưu user
            session.setAttribute("user", user);

            // timeout 30 phút
            session.setMaxInactiveInterval(30 * 60);

            // Redirect trang chủ
            response.sendRedirect(request.getContextPath() + "/tetris-mvc/index.jsp"
            );

        }

        // Thất bại
        else {

            request.setAttribute(
                    "error",
                    "Email hoặc mật khẩu không đúng!"
            );

            request.getRequestDispatcher(
                    "/tetris-mvc/public/login.jsp"
            ).forward(request, response);
        }
    }

    /**
     * Hash SHA-256
     */
    private String hashPassword(String password) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    md.digest(password.getBytes());

            StringBuilder sb =
                    new StringBuilder();

            for (byte b : hashBytes) {
                sb.append(
                        String.format("%02x", b)
                );
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }
    }
}