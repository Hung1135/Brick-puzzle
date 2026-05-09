package vn.edu.hcmuaf.fit.ttltw_nhom6.model;

import java.sql.Timestamp;

public class GameScore {

    private long id;
    private long userId;
    private int score;
    private int level;
    private int linesCleared;
    private int playTimeSeconds;
    private Timestamp createdAt;

    public GameScore() {
    }

    public GameScore(long id, long userId, int score,
                     int level, int linesCleared,
                     int playTimeSeconds, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.level = level;
        this.linesCleared = linesCleared;
        this.playTimeSeconds = playTimeSeconds;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }

    public int getPlayTimeSeconds() {
        return playTimeSeconds;
    }

    public void setPlayTimeSeconds(int playTimeSeconds) {
        this.playTimeSeconds = playTimeSeconds;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}