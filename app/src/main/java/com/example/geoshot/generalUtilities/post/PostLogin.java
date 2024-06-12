package com.example.geoshot.generalUtilities.post;

import com.example.geoshot.generalUtilities.APIClient;

public class PostLogin {
    public static String post(String username, String password) {
        APIClient bul = new APIClient("/api/login");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","password"),username,password);
        return PostRequest.post(url,body);
    }
}
