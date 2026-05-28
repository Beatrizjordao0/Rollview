package com.example.rollview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMDBMovieResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genres")
    private List<TMDBGender> genres;

    public List<TMDBGender> getGenres() {
        return genres;
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() { return voteAverage; }

    public int getRuntime() {
        return runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}