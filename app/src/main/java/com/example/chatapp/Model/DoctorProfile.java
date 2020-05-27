package com.example.chatapp.Model;

public class DoctorProfile {
    String id;
    String firstName;
    String lastName;
    String startTime;
    String endTime;
    String uid;
    public DoctorProfile(){

    }
    public DoctorProfile(String id, String firstName, String lastName, String startTime, String endTime,String uid){
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.startTime=startTime;
        this.endTime=endTime;
        this.uid=uid;
    }

    public String getId()
    {
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public String getUID(){return uid;}
}

