package vn.edu.hcmuaf.fit.ttltw_nhom6.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.edu.hcmuaf.fit.ttltw_nhom6.dao.ScoreDAO;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.GameScore;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.User;

import java.io.IOException;

@WebServlet("/save-score")
public class SaveScoreServlet extends HttpServlet {

    private final ScoreDAO scoreDAO =
            new ScoreDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        // chưa login
        if(session == null ||
                session.getAttribute("user") == null){

            response.setStatus(401);
            return;
        }

        User user =
                (User) session.getAttribute("user");

        // đọc JSON
        Gson gson = new Gson();

        GameScore score =
                gson.fromJson(
                        request.getReader(),
                        GameScore.class
                );

        // set user id
        score.setUserId(user.getId());

        // save
        boolean success =
                scoreDAO.saveScore(score);

        response.setContentType("application/json");

        response.getWriter().write(
                "{\"success\": " + success + "}"
        );
    }
}