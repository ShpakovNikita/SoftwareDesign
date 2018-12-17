package com.example.shaft.softwaredesign.firebase.workers.extensions;

import com.example.shaft.softwaredesign.firebase.workers.entity.FirebaseAccount;
import com.example.shaft.softwaredesign.model.Account;

public class AccountExtensions {
    public static Account castToExternal(FirebaseAccount entity) {
        if (entity == null) {
            return null;
        }
        Account account = new Account();
        account.setFirstName(entity.getFirstName());
        account.setLastName(entity.getLastName());
        account.setAddress(entity.getAddress());
        account.setEmail(entity.getEmail());
        account.setPicture(entity.getPicture());

        return account;
    }


    public static FirebaseAccount castToFirebase(Account account) {
        if (account == null) {
            return null;
        }
        FirebaseAccount entity = new FirebaseAccount();
        entity.setFirstName(account.getFirstName());
        entity.setLastName(account.getLastName());
        entity.setAddress(account.getAddress());
        entity.setEmail(account.getEmail());
        entity.setPicture(account.getPicture());

        return entity;
    }

}
