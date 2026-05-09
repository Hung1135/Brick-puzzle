package vn.edu.hcmuaf.fit.ttltw_nhom6.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.ttltw_nhom6.db.JdbiConnector;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.GameScore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameScoreDAO {

    private final Jdbi jdbi;

    public GameScoreDAO() {
        this.jdbi = JdbiConnector.get();
    }

    // Lưu điểm
    public boolean saveScore(GameScore score) {
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(
                            "INSERT INTO game_scores (user_id, score, level, lines_cleared, play_time_seconds, created_at) " +
                                    "VALUES (:userId, :score, :level, :linesCleared, :playTimeSeconds, NOW())")
                    .bindBean(score)
                    .execute();
            return rows > 0;
        });
    }

    // Lấy top điểm
    public List<GameScore> getTopScores(int limit) {
        return jdbi.withHandle(handle -> handle.createQuery(
                        "SELECT id, user_id, score, level, lines_cleared, play_time_seconds, created_at " +
                                "FROM game_scores ORDER BY score DESC LIMIT :limit")
                .bind("limit", limit)
                .mapToBean(GameScore.class)
                .list());
    }

    // Lịch sử điểm của người dùng
    public List<GameScore> getHistoryByUser(long userId) {
        return jdbi.withHandle(handle -> handle.createQuery(
                        "SELECT id, user_id, score, level, lines_cleared, play_time_seconds, created_at " +
                                "FROM game_scores WHERE user_id = :userId ORDER BY created_at DESC")
                .bind("userId", userId)
                .mapToBean(GameScore.class)
                .list());
    }
}