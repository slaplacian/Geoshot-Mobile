package com.example.geoshot.utils;

import com.example.geoshot.R;

public class PostLogin implements PostStrategy{
    private static final String URL_LOGIN = "http://"+ R.string.IP_SERVER_ADDRESS + ":8080/api/login";
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
        return URL_LOGIN;
    }
}
