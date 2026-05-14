package com.example.rollview;

import com.google.gson.annotations.SerializedName;

public class TMDBCast {
    @SerializedName("name")
    private String name;
    @SerializedName("character")
    private String character;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("job")
    private String job;

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfilePath() {
        return profilePath;
    }
    public String getJob() {
        if(job != null && !job.isEmpty()){
         if(job.equals("Director") || job.equals("Diretor")){
             return "Diretor";
            }
            return job;
        }
        return character;
    };
}