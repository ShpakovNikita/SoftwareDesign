package com.example.shaft.softwaredesign.databaseWorkers;

import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.model.Picture;

public class Extensions {
    public static Account castToExternal(AccountEntity entity) {
        if (entity == null) {
            return null;
        }
        Account account = new Account();
        account.setId(entity.id);
        account.setFirstName(entity.firstName);
        account.setLastName(entity.lastName);
        account.setAddress(entity.address);
        account.setEmail(entity.email);

        Picture picture = new Picture();
        picture.setPath(entity.picture);
        account.setPicture(picture);

        return account;
    }


    public static AccountEntity castToDatadase(Account account) {
        if (account == null) {
            return null;
        }
        AccountEntity entity = new AccountEntity();
        entity.id = account.getId();
        entity.firstName = account.getFirstName();
        entity.lastName = account.getLastName();
        entity.address = account.getAddress();
        entity.email = account.getEmail();
        if (account.getPicture() != null) {
            entity.picture = account.getPicture().getPath();
        }

        return entity;
    }

}
