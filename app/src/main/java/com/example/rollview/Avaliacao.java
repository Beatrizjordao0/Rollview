package com.example.rollview;

import android.net.Uri;

public class Avaliacao {

    private String data;
    private String texto;
    private float nota;
    private Uri posterFilme;

    public Avaliacao(String data, String texto, float nota, Uri posterFilme) {
        this.data = data;
        this.texto = texto;
        this.nota = nota;
        this.posterFilme = posterFilme;
    }

    public String getData() {
        return data;
    }

    public String getTexto() {
        return texto;
    }

    public float getNota() {
        return nota;
    }

    public Uri getPosterFilme() {
        return posterFilme;
    }
}