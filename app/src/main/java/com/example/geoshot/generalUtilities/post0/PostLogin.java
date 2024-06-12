package com.example.geoshot.generalUtilities.post0;

public class PostLogin {
    public static String post(String username, String password) {
        APIClientPost bul = new APIClientPost("/api/login");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","password"),username,password);
        return PostRequest.post(url,body);
    }
}
