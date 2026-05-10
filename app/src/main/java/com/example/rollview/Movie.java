package com.example.rollview;

public class Movie {
    private int id;
    private String title;
    private String poster_path;
    private String backdrop_path;

    public Movie(int id, String title, String poster_path, String backdrop_path) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }
    public int getId(){
        return  id;
    }
    public String getTitle(){
        return  title;
    }
    public String getPosterUrl(){
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
    public String getBackdrop_path(){
        return "https://image.tmdb.org/t/p/w780" + backdrop_path;
    }
}
