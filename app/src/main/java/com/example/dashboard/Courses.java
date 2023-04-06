package com.example.dashboard;

public class Courses {
    private String name, email, password, repassword;

    public Courses() {
    }

    // Constructor for all variables.
    public Courses(String email, String name) {
        this.name = name;
        this.email = email;

    }

    // getter methods for all variables.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // getter methods for all variables.
    public String getEmail() {
        return email;
    }

    // setter method for all variables.
    public void setEmail(String email) {
        this.email = email;
    }
}
