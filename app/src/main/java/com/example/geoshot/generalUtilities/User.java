package com.example.geoshot.generalUtilities;

public class User {
    private final String nome;
    private final String username;
    private final String email;
    private final String senha;
    private final String confirmarSenha;

    public User(String nome, String username, String email, String senha, String confirmarSenha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
    }

    public User(String username, String senha){
        this(null, username, null, senha, null);
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }
}
