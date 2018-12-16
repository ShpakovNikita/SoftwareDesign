package com.example.shaft.softwaredesign.databaseWorkers.extensions;

import com.example.shaft.softwaredesign.databaseWorkers.entity.AccountEntity;
import com.example.shaft.softwaredesign.model.Account;

public class AccountExtensions {
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

        account.setPicture(entity.picture);

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
        entity.picture = account.getPicture();

        return entity;
    }

}
