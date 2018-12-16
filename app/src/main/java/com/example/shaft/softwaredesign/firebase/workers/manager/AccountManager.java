package com.example.shaft.softwaredesign.firebase.workers.manager;

import android.content.Context;

import com.example.shaft.softwaredesign.firebase.workers.AccountListeners;
import com.example.shaft.softwaredesign.firebase.workers.state.AccountState;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.firebase.auth.AuthManager;
import com.example.shaft.softwaredesign.firebase.workers.AccountProvider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AccountManager {

    private static AccountManager instance;
    private AccountProvider provider;
    private MutableLiveData<AccountState> accountState = new MutableLiveData<>();

    private AccountManager() {
        provider = new AccountProvider();
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public String getCurrentUserEmail() {
        return AuthManager.getInstance().getCurrentUserEmail();
    }

    public String getCurrentUserId() {
        return AuthManager.getInstance().getCurrentUserId();
    }

    public LiveData<AccountState> getCurrentAccount() {
        String userId = AuthManager.getInstance().getCurrentUserId();
        provider.getUserById(userId, new AccountListeners.LoadAccountListener() {
            @Override
            public void onSuccess(Account data) {
                AccountState state = new AccountState();
                state.data = data;
                state.isSuccess = true;
                accountState.setValue(state);
            }

            @Override
            public void onError(String msg) {
                AccountState state = new AccountState();
                state.error = msg;
                accountState.setValue(state);
            }

            @Override
            public void onNetworkError() {
                AccountState state = new AccountState();
                state.isNetworkErrorOccurred = true;
                state.error = "Check your connection!";
                accountState.setValue(state);
            }
        });

        return accountState;
    }

    public LiveData<AccountState> updateAccount(Account account) {
        provider.updateAccount(AuthManager.getInstance().getCurrentUserId(),
                account,
                new AccountListeners.SaveAccountListener() {
            @Override
            public void onSuccess() {
                AccountState state = new AccountState();
                state.isSuccess = true;
                accountState.setValue(state);
            }

            @Override
            public void onNetworkError() {
                AccountState state = new AccountState();
                state.isNetworkErrorOccurred = true;
                state.error = "Check your connection!";
                accountState.setValue(state);
            }

            @Override
            public void onError(String msg) {
                AccountState state = new AccountState();
                state.error = msg;
                accountState.setValue(state);
            }
        });

        return accountState;
    }

    public LiveData<AccountState> createAccount(Account account) {
        provider.createAccount(AuthManager.getInstance().getCurrentUserId(),
                account,
                new AccountListeners.SaveAccountListener() {
            @Override
            public void onSuccess() {
                AccountState state = new AccountState();
                state.isSuccess = true;
                accountState.setValue(state);
            }

            @Override
            public void onNetworkError() {
                AccountState state = new AccountState();
                state.isNetworkErrorOccurred = true;
                state.error = "Check your connection!";
                accountState.setValue(state);
            }

            @Override
            public void onError(String msg) {
                AccountState state = new AccountState();
                state.error = msg;
                accountState.setValue(state);
            }
        });

        return accountState;
    }
}
