package com.example.geoshot.generalUtilities.get;

public class GetEditPerfil {
    public static String get(String username) {

        APIClientGet bul = new APIClientGet("/api/edit-perfil");
        String url = String.format(bul.PrepareStringRequest("username"),username);

        return GetRequest.get(url);
    }
}
