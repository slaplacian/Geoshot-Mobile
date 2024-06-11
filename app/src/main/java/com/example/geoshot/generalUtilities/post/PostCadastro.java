package com.example.geoshot.generalUtilities.post;


import com.example.geoshot.generalUtilities.AuxiliarGeral;
import com.example.geoshot.generalUtilities.User;

public class PostCadastro implements PostStrategy {
    private static final String URL_CADASTRO = "http://"+ AuxiliarGeral.getIPServerAddress() +":8080/api/signup";
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