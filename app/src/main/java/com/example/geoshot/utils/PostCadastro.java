package com.example.geoshot.utils;


import com.example.geoshot.R;

public class PostCadastro implements PostStrategy {
    private static final String URL_CADASTRO = "http://"+ R.string.IP_SERVER_ADDRESS +":8080/api/signup";
    private final User user;

    public PostCadastro(User user){
        this.user = user;
    }
    @Override
    public String json() {
       return "{\"name\": \"" + user.getNome() + "\"," +
                "\"username\": \"" + user.getUsername() + "\"," +
                "\"email\": \"" + user.getEmail() + "\"," +
                "\"password\": \"" + user.getSenha() + "\"," +
                "\"confirm-password\": \"" + user.getConfirmarSenha() + "\"}";
    }

    @Override
    public String url() {
        return URL_CADASTRO;
    }
}
