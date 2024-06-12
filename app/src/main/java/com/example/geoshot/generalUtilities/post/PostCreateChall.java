package com.example.geoshot.generalUtilities.post;

public class PostCreateChall {
    public static String post(String username, String photo, String answer) {
        APIClientPost bul = new APIClientPost("/api/create-chall");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","photo","answer"),username,photo,answer);
        return PostRequest.post(url,body);
    }
}