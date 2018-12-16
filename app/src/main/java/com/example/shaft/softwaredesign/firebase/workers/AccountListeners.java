package com.example.shaft.softwaredesign.firebase.workers;

import com.example.shaft.softwaredesign.model.Account;

public class AccountListeners {
    public interface LoadAccountListener {
        void onSuccess(Account account);
        void onError(String msg);
        void onNetworkError();
    }

    public interface SaveAccountListener {
        void onSuccess();
        void onError(String msg);
        void onNetworkError();
    }

}
