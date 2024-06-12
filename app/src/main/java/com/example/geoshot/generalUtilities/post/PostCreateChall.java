package com.example.geoshot.generalUtilities.post;

import com.example.geoshot.generalUtilities.APIClient;

public class PostCreateChall {
    public static String post(String username, String photo, String answer) {
        APIClient bul = new APIClient("/api/create-chall");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","photo","answer"),username,photo,answer);
        return PostRequest.post(url,body);
    }
}