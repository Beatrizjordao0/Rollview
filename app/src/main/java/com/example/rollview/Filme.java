package com.example.rollview;

public class Filme {

    private String titulo;
    private String ano;
    private String diretor;
    private String sinopse;

    private double nota;

    private int duracao;

    private String posterUrl;
    private String backgroundUrl;

    public Filme(
            String titulo,
            String ano,
            String diretor,
            String sinopse,
            double nota,
            int duracao,
            String posterUrl,
            String backgroundUrl
    ) {

        this.titulo = titulo;
        this.ano = ano;
        this.diretor = diretor;
        this.sinopse = sinopse;
        this.nota = nota;
        this.duracao = duracao;
        this.posterUrl = posterUrl;
        this.backgroundUrl = backgroundUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAno() {
        return ano;
    }

    public String getDiretor() {
        return diretor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public double getNota() {
        return nota;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }
}