package com.cap.pojo;

public class CollectionData {
    private int collectionId;
    private int userId;
    private String dataId;
    private String collectionTime;

    public CollectionData() {
    }

    @Override
    public String toString() {
        return "CollectionData{" +
                "collectionId=" + collectionId +
                ", userId=" + userId +
                ", dataId='" + dataId + '\'' +
                ", collectionTime='" + collectionTime + '\'' +
                '}';
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
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

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public CollectionData(int collectionId, int userId, String dataId, String collectionTime) {
        this.collectionId = collectionId;
        this.userId = userId;
        this.dataId = dataId;
        this.collectionTime = collectionTime;
    }
}
