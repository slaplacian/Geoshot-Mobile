package com.example.geoshot.generalUtilities.get;

public class GetMyAttempts {
    public static String get(String username) {

        APIClientGet bul = new APIClientGet("/api/my-attempts");
        String url = String.format(bul.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }
}
