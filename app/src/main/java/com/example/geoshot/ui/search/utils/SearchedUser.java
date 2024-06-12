package com.example.geoshot.ui.search.utils;

public class SearchedUser {
    private String username, photo, followshipState;

    public SearchedUser(String username, String photo, String followshipState) {
        this.username = username;
        this.photo = photo;
        this.followshipState = followshipState;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    /* Returns "Unfollow" or "Follow" based on followshipState */
    public String getFollowshipState() {
        if(followshipState.equals("true")){
            return "Unfollow";
        }
        else{
            return "Follow";
        }
    }

    public void setFollowshipState(String followshipState) {
        this.followshipState = followshipState;
    }
}
