package com.example.geoshot.generalUtilities.post;

import android.util.Log;

import com.example.geoshot.generalUtilities.APIClient;

public class PostSignup {
    public static String post(String name, String username, String email, String password, String confirmPassword) {
        APIClient bul = new APIClient("/api/signup");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("name","username","email","password","confirm-password"),
                name,username,email,password,confirmPassword);
        return PostRequest.post(url,body);
    }
}
