package com.example.rollview;

public class Movie {
    private int id;
    private String title;
    private int poster_path;

    public Movie(int id, String title, int poster_path) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
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
}
