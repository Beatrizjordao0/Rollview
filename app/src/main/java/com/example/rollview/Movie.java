package com.example.rollview;

public class    Movie {
    private int id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private double vote_average;

    public Movie(int id, String title, String poster_path, String backdrop_path, String release_date, double vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
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
    public String getYear() {
        if(release_date != null && release_date.length() >= 4) {
            return  release_date.substring(0, 4);
        }
        return  "N/A";
    }
    // Formatar a Nota
    public String getFormattedRating(){
        return String.format("%.1f", vote_average).replace('.', ',');
    }
}
