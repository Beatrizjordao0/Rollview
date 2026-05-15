package com.example.rollview;

import com.google.gson.annotations.SerializedName;

public class TMDBGender {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {return id; }

    public String getName() {return name; }
}
