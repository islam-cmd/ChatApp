package com.example.chatapp.Model;

public class UserInfo {
    String id;
    String firstName;
    String lastName;
    String dateOfBirth;
    public UserInfo(){

    }
    public UserInfo(String id, String firstName, String lastName,String dateOfBirth){
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
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
    public String getDateOfBirth(){return dateOfBirth;}
}

