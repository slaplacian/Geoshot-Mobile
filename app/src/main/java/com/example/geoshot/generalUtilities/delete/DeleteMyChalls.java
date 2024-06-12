package com.example.geoshot.generalUtilities.delete;

import com.example.geoshot.generalUtilities.get.APIClientGet;

public class DeleteMyChalls {
    public static String get(String username) {

        APIClientGet bul = new APIClientGet("/api/my-challs");
        String url = String.format(bul.PrepareStringRequest("username"),username);

        return DeleteRequest.delete(url);
    }
}
