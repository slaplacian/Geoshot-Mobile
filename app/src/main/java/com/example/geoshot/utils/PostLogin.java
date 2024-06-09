package com.example.geoshot.utils;

public class PostLogin implements PostStrategy{
    private static final String URL_LOGIN = "http://192.168.0.42:8080/api/login";
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
