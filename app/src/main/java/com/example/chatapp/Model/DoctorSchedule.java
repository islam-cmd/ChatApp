package com.example.chatapp.Model;

public class DoctorSchedule {
    String id;
    String firstName;
    String lastName;
    String startTime;
    String endTime;

    public DoctorSchedule(){

    }
    public DoctorSchedule(String id, String firstName, String lastName, String startTime,String endTime){
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.startTime=startTime;
        this.endTime=endTime;
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
}
