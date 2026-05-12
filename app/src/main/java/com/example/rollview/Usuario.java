package com.example.rollview;

import android.net.Uri;

import java.util.List;

public class Usuario {

    private String nome;
    private String username;
    private Uri fotoPerfil;
    private List<Avaliacao> avaliacoes;

    public Usuario(String nome, String username, Uri fotoPerfil, List<Avaliacao> avaliacoes) {
        this.nome = nome;
        this.username = username;
        this.fotoPerfil = fotoPerfil;
        this.avaliacoes = avaliacoes;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public Uri getFotoPerfil() {
        return fotoPerfil;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
}