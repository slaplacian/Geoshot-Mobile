package com.example.geoshot.generalUtilities.get;

public class GetMyChalls {

    public static String get(String username) {

        APIClientGet bul = new APIClientGet("/api/my-challs");
        String url = String.format(bul.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }

}
