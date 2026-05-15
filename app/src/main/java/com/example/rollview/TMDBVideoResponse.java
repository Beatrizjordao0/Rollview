package com.example.rollview;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TMDBVideoResponse {

    @SerializedName("results")
    private List<TMDBVideo> results;

    public List<TMDBVideo> getResults() {
        return results;
    }

    public static class TMDBVideo {

        @SerializedName("key")
        private String key;

        @SerializedName("name")
        private String name;

        @SerializedName("site")
        private String site;

        @SerializedName("type")
        private String type;

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }
        public String getSite() {
            return site;
        }

        public String getType() {
            return type;
        }
    }
}