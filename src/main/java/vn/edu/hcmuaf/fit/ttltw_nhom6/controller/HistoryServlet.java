package vn.edu.hcmuaf.fit.ttltw_nhom6.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.ttltw_nhom6.dao.ScoreDAO;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.User;

import java.io.IOException;
@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private final ScoreDAO scoreDAO =
            new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if(session == null ||
                session.getAttribute("user") == null){

            response.sendRedirect(
                    request.getContextPath()
                            + "/tetris-mvc/public/login.jsp"
            );

            return;
        }

        User user =
                (User) session.getAttribute("user");

        request.setAttribute(
                "scores",
                scoreDAO.getScoresByUser(user.getId())
        );

        request.getRequestDispatcher(
                "/tetris-mvc/public/history-score.jsp"
        ).forward(request, response);
    }
}