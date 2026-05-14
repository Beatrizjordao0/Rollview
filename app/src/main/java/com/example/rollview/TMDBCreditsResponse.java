package com.example.rollview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMDBCreditsResponse {
    @SerializedName("cast")
    private List<TMDBCast> cast;

    @SerializedName("crew")
    private List<TMDBCast> crew;

    public List<TMDBCast> getCast() {
        return cast;
    }
    public List<TMDBCast> getCrew() {return crew; }
}