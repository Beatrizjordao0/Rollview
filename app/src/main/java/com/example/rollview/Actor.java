package com.example.rollview;

public class Actor {
    private String name;
    private String role;
    private String imageUrl;

    public Actor(String name, String role, String imageUrl) {
        this.name = name;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}