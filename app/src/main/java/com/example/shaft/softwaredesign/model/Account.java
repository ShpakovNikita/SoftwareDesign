package com.example.shaft.softwaredesign.model;

public class Account {

    private String id;
    private String picture;
    private String firstname;
    private String lastname;
    private String address;
    private String email;

    public Account(){
        picture = null;
        firstname = "John";
        lastname = "Doe";
        address = "Kaukaz";
        email = "Email";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
