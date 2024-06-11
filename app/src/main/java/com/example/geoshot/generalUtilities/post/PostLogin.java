package com.example.geoshot.generalUtilities.post;

import android.util.Log;

import com.example.geoshot.generalUtilities.AuxiliarGeral;
import com.example.geoshot.generalUtilities.User;

public class PostLogin implements PostStrategy {
    private static final String URL_LOGIN = "http://"+ AuxiliarGeral.getIPServerAddress() + ":8080/api/login";
    private final User user;

    public PostLogin(User user){
        this.user = user;
    }
    @Override
    public String json() {
        return "{\"username\": \"" + user.getUsername() + "\"," +
                "\"password\": \"" + user.getSenha() + "\"}";
    }

    @Override
    public String url() {
        Log.d("Depurando", "URL chamada em postLogin: " + URL_LOGIN);
        return URL_LOGIN;
    }
}
