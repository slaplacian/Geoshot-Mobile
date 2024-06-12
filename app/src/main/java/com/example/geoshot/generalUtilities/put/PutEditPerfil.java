package com.example.geoshot.generalUtilities.put;

import com.example.geoshot.generalUtilities.APIClient;


public class PutEditPerfil {
    public static String put(String username, String photo, String oldPassword, String password, String confirmPassword) {
        APIClient bul = new APIClient("/api/edit-perfil");
        String url  = bul.PrepareStringRequest();
        String body = String.format(bul.PrepareStringBody("username","photo","old-password","password","confirm-password"),
                username,photo,oldPassword,password,confirmPassword);
        return PutRequest.put(url,body);
    }
}
