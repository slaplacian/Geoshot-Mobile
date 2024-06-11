package com.example.geoshot.ui.myChallenges.utils;

public class MyChallengesItem {
    private int pubId, ownerUserId;
    private String photo, correctValue;

    public MyChallengesItem(int pubId, int ownerUserId, String photo, String correctValue) {
        this.pubId = pubId;
        this.ownerUserId = ownerUserId;
        this.photo = photo;
        this.correctValue = correctValue;
    }

    public int getPubId() {
        return pubId;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCorrectValue() {
        return correctValue;
    }
}
