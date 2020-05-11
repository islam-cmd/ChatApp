package com.example.chatapp.Model;

public class Patient {
    private String  AdditionalNotes;
    private String id;
    private Double time;
    private String username;

    public Patient(String id, String username, Double time, String additionalNotes) {
        this.id = id;
        this.username = username;
        this.time = time;
        this.AdditionalNotes = additionalNotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }



    public String getAdditionalNotes() {
        return AdditionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        AdditionalNotes = additionalNotes;
    }

}
