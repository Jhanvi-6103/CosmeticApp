package com.app.cosmeticapp;

public class User {
    public String name;
     public String email;

    public User() {
        // Required empty constructor for Firebase
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
