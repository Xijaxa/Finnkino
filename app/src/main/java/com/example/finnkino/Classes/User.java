package com.example.finnkino.Classes;

public class User {
    private String profileName, lastName, email, userName;
    public User(String profilename, String lastname, String email, String username){

        this.profileName = profilename;
        this.email = email;
        this.lastName = lastname;
        this.userName = username;

    }
    public String getProfileName(){
        return profileName;
    }

    public String getLastname(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getUserName(){
        return userName;
    }
}
