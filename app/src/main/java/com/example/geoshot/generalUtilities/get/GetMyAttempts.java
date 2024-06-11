package com.example.geoshot.generalUtilities.get;

public class GetMyAttempts {
    public static String get(String username) {

        APIClientGet intialPagePSR = new APIClientGet("/api/my-attempts");
        String url = String.format(intialPagePSR.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }
}
