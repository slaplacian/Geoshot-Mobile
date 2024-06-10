package com.example.geoshot.ui.home.utils;

public class FeedItem {
    private int pubId;
    private String photo, userPhoto, dateOfCreation, username;

    public FeedItem(int pubId, String photo, String userPhoto, String dateOfCreation, String username) {
        this.pubId = pubId;
        this.photo = photo;
        this.userPhoto = userPhoto;
        this.dateOfCreation = dateOfCreation;
        this.username = username;
    }

    public int getPubId() {
        return pubId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getUsername() {
        return username;
    }

}
