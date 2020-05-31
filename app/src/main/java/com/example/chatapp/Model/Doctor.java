package com.example.chatapp.Model;

public class Doctor {
    private String id;
    private String username;
    private String imageURL;


    private String email;

    public Doctor(String email, String id, String imageURL, String username) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.email = email;

    }

    public Doctor() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
