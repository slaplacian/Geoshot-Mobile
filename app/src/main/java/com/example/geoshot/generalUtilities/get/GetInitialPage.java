package com.example.geoshot.generalUtilities.get;

public class GetInitialPage {
    public static String get(String username) {

        APIClientGet intialPagePSR = new APIClientGet("/api/initial-page");
        String url = String.format(intialPagePSR.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }

}
