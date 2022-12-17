package com.example.whatsappclone.model;

public class MessageModel {

    String userID, message;
    Long timeStamp;

    public MessageModel(String userID, String message, Long timeStamp) {
        this.userID = userID;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessageModel(String userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    public MessageModel() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
