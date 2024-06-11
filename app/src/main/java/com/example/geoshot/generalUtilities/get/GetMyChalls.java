package com.example.geoshot.generalUtilities.get;

public class GetMyChalls {

    public static String get(String username) {

        APIClientGet intialPagePSR = new APIClientGet("/api/my-challs");
        String url = String.format(intialPagePSR.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }

}
