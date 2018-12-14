package com.example.shaft.softwaredesign.model;

public class Account {

    private int mId;
    private String mPicture;
    private String mFirstName;
    private String mLastName;
    private String mAddress;
    private String mEmail;

    public Account(){
        mPicture = null;
        mFirstName = "John";
        mLastName = "Doe";
        mAddress = "Kaukaz";
        mEmail = "Email";
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        this.mPicture = picture;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }
}
