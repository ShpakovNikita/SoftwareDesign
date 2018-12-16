package com.example.shaft.softwaredesign.databaseWorkers.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class AccountEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String picture;
    public String firstName;
    public String lastName;
    public String address;
    public String email;

}