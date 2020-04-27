package com.example.chatapp.Model;

public class UserInfo {
    String id;
    String firstName;
    String lastName;
    public UserInfo(){

    }
    public UserInfo(String id, String firstName, String lastName){
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

