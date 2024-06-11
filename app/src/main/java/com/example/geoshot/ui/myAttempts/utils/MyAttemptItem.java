package com.example.geoshot.ui.myAttempts.utils;

public class MyAttemptItem {
    private int pubId;
    private double accuracy;
    private String username, photo, userphoto, attemptDate;

    public MyAttemptItem(int pubId, double accuracy, String username, String photo, String userphoto, String attemptDate) {
        this.pubId = pubId;
        this.accuracy = accuracy;
        this.username = username;
        this.photo = photo;
        this.userphoto = userphoto;
        this.attemptDate = attemptDate;
    }

    public int getPubId() {
        return pubId;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public String getAttemptDate() {
        return attemptDate;
    }
}
