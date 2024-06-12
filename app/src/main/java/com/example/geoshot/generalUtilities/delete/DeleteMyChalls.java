package com.example.geoshot.generalUtilities.delete;

import com.example.geoshot.generalUtilities.get.APIClientGet;

public class DeleteMyChalls {
    public static String delete(String username, String pubId) {

        APIClientGet bul = new APIClientGet("/api/my-challs");
        String url = String.format(bul.PrepareStringRequest("username", "pub-id"),username, pubId);

        return DeleteRequest.delete(url);
    }
}
