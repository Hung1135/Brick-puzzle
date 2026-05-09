package vn.edu.hcmuaf.fit.ttltw_nhom6.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.ttltw_nhom6.db.JdbiConnector;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.GameScore;

import java.util.List;

public class ScoreDAO {

    private final Jdbi jdbi;

    public ScoreDAO() {
        this.jdbi = JdbiConnector.get();
    }

    // lưu điểm
    public boolean saveScore(GameScore score) {

        String sql =
                "INSERT INTO game_scores " +
                        "(user_id, score, level, lines_cleared, play_time_seconds) " +
                        "VALUES " +
                        "(:userId, :score, :level, :linesCleared, :playTimeSeconds)";

        return jdbi.withHandle(handle -> {

            int rows = handle.createUpdate(sql)

                    .bindBean(score)

                    .execute();

            return rows > 0;
        });
    }

    // lịch sử điểm
    public List<GameScore> getScoresByUser(Long userId) {

        String sql =
                "SELECT * FROM game_scores " +
                        "WHERE user_id = :userId " +
                        "ORDER BY created_at DESC";

        return jdbi.withHandle(handle ->

                handle.createQuery(sql)

                        .bind("userId", userId)

                        .mapToBean(GameScore.class)

                        .list()
        );
    }
}