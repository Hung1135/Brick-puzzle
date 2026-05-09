package vn.edu.hcmuaf.fit.ttltw_nhom6.model;

import java.sql.Timestamp;

public class GameHistory {

    private long id;
    private long userId;
    private long scoreId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String deviceInfo;
    private String gameVersion;
    private String result;

    public GameHistory() {
    }

    public GameHistory(long id, long userId, long scoreId,
                       Timestamp startTime, Timestamp endTime,
                       String deviceInfo, String gameVersion,
                       String result) {
        this.id = id;
        this.userId = userId;
        this.scoreId = scoreId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deviceInfo = deviceInfo;
        this.gameVersion = gameVersion;
        this.result = result;
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

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}