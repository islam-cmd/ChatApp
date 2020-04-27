package com.example.chatapp.Model;

public class DoctorSchedule {
    String id;
    String firstName;
    String lastName;
    public DoctorSchedule(){

    }
    public DoctorSchedule(String id, String firstName, String lastName){
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
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
}
