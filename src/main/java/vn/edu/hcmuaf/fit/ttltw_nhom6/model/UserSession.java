package vn.edu.hcmuaf.fit.ttltw_nhom6.model;

import java.sql.Timestamp;

public class UserSession {

    private long id;
    private long userId;
    private String refreshToken;
    private Timestamp expiredAt;
    private Timestamp createdAt;

    public UserSession() {
    }

    public UserSession(long id, long userId,
                       String refreshToken,
                       Timestamp expiredAt,
                       Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}