package com.example.geoshot.generalUtilities.put;

import com.example.geoshot.generalUtilities.APIClient;

public class PutToggleFollowship {
    public static String put(String username, String searchedUsername) {
        APIClient bul = new APIClient("/api/toggle-followship");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","searched-username"),
                username,searchedUsername);
        return PutRequest.put(url,body);
    }
}
