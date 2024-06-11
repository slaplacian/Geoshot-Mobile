package com.example.geoshot.generalUtilities.get;

public class GetSearch {
    public static String get(String username, String searchedUsername) {

        APIClientGet intialPagePSR = new APIClientGet("/api/search");
        String url = String.format(intialPagePSR.PrepareStringRequest("username","searched-username"),username,searchedUsername);

        return GetRequest.get(url);
    }
}
