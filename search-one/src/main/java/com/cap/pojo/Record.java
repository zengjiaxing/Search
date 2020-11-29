package com.cap.pojo;

public class Record {
    int recordId;
    int userId;
    String dataId;
    String recordTime;

    public Record(int recordId, int userId, String dataId, String dataTime) {
        this.recordId = recordId;
        this.userId = userId;
        this.dataId = dataId;
        this.recordTime = dataTime;
    }

    public Record() {
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", dataId='" + dataId + '\'' +
                ", dataTime='" + recordTime + '\'' +
                '}';
    }
}
