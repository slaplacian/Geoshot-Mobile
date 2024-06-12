package com.example.geoshot.generalUtilities.get;

public class GetInitialPage {
    public static String get(String username) {

        APIClientGet bul = new APIClientGet("/api/initial-page");
        String url = String.format(bul.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }

}
