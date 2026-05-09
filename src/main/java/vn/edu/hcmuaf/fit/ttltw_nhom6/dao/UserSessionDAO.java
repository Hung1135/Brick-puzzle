package vn.edu.hcmuaf.fit.ttltw_nhom6.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.ttltw_nhom6.db.JdbiConnector;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.UserSession;

import java.time.LocalDateTime;

public class UserSessionDAO {

    private final Jdbi jdbi;

    public UserSessionDAO() {
        this.jdbi = JdbiConnector.get();
    }

    // Lưu session
    public boolean saveSession(UserSession session) {
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(
                            "INSERT INTO user_sessions (user_id, refresh_token, expired_at, created_at) " +
                                    "VALUES (:userId, :refreshToken, :expiredAt, NOW())")
                    .bindBean(session)
                    .execute();
            return rows > 0;
        });
    }

    // Tìm session bằng refresh token
    public UserSession findSessionByToken(String refreshToken) {
        return jdbi.withHandle(handle -> handle.createQuery(
                        "SELECT id, user_id, refresh_token, expired_at, created_at " +
                                "FROM user_sessions WHERE refresh_token = :token")
                .bind("token", refreshToken)
                .mapToBean(UserSession.class)
                .findFirst()
                .orElse(null));
    }

    // Xóa session theo token
    public int deleteSessionByToken(String refreshToken) {
        return jdbi.withHandle(handle -> handle.createUpdate(
                        "DELETE FROM user_sessions WHERE refresh_token = :token")
                .bind("token", refreshToken)
                .execute());
    }
}