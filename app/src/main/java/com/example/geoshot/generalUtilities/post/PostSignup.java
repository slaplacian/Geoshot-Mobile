package com.example.geoshot.generalUtilities.post;

import android.util.Log;

public class PostSignup {
    public static String post(String name, String username, String email, String password, String confirmPassword) {
        APIClientPost bul = new APIClientPost("/api/signup");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("name","username","email","password","confirm-password"),
                name,username,email,password,confirmPassword);
        Log.d("MAIS GAY",body);
        return PostRequest.post(url,body);
    }
}
