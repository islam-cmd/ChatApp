package com.example.chatapp.Model;

public class Doctor {
    private String id, username, imageURL;

    public Doctor(String id, String username, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL() {
        return imageURL;
    }
}
