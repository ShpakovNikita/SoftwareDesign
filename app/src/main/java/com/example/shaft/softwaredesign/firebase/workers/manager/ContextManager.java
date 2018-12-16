package com.example.shaft.softwaredesign.firebase.workers.manager;

import android.content.Context;

import com.example.shaft.softwaredesign.firebase.workers.Database;
import com.example.shaft.softwaredesign.model.Account;
import com.google.firebase.auth.FirebaseAuth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContextManager {

    private static FirebaseAuth auth;
    private static ContextManager instance;

    private Database database;

    private MutableLiveData<Account> account = new MutableLiveData<>();

    private ContextManager(Context context) {
        database = Database.getInstance(context);
        auth = FirebaseAuth.getInstance();
        setAccount(new Account());

    }


    public LiveData<Account> getAccount() {
        return account;
    }

    public void setAccount(Account data){
        account.postValue(data);
    }

    public static ContextManager getInstance(Context context) {
        if (instance == null) {
            // TODO
            synchronized (ContextManager.class) {
                if (instance == null) {
                    instance = new ContextManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void saveAccount(Account account) {
    }

    public void updateAccount(Account account) {
    }
}
