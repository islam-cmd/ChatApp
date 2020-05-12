package com.example.chatapp.Model;

public class UserInfo {
    String id;
    String firstName;
    String lastName;
    String dateOfBirth;
    String personalInfo;
    String UID;
    public UserInfo(){

    }
    public UserInfo(String id, String firstName, String lastName,String dateOfBirth,String personalInfo, String UID){
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.personalInfo=personalInfo;
        this.UID=UID;
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
    public String getPersonalInfo(){return personalInfo;}
    public String getUID(){return  UID;}
}

