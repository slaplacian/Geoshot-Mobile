package com.example.geoshot.generalUtilities.post;

import com.example.geoshot.generalUtilities.APIClient;

public class PostChall {
    public static String post(String username, String pubId, String userAnswer) {
        APIClient bul = new APIClient("/api/chall");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","pub-id","user-answer"),username,pubId,userAnswer);
        return PostRequest.post(url,body);
    }
}
