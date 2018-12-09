package com.example.shaft.softwaredesign.viewmodels;

import android.app.Application;

import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.repository.AccountRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AccountViewModel extends AndroidViewModel {

    private Account account;

    public AccountViewModel(Application application) {
        super(application);

        account = AccountRepository.getInstance(application.getApplicationContext())
                .getMainAccount();
    }

    public Account getAccount() {
        return account;
    }
}
