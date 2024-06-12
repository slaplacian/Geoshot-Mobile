package com.example.geoshot.generalUtilities.get;

public class GetPerfil {
    public static String get(String username) {

        APIClientGet intialPagePSR = new APIClientGet("/api/perfil");
        String url = String.format(intialPagePSR.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }
}
