package com.example.rollview;

import java.util.ArrayList;
import java.util.List;

public class Sessao {
    public static String nomeUsuario = "";
    public static String username = "";
    public static List<Avaliacao> avaliacoes = new ArrayList<>();
    public static List<TMDBMovieResponse> favorites = new ArrayList<>();
    public static TMDBMovieResponse filmeAtual = null;
    public static boolean isFavorite(int movieId) {
        for (TMDBMovieResponse f : favorites) {
            if(f.getId() == movieId) return true;
        }
        return false;
    }
}