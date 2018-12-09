package com.example.shaft.softwaredesign.viewmodels;

import android.app.Application;

import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.repository.AccountRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EditAccountViewModel extends AndroidViewModel {

    private Account account;

    private Account editedAccount;

    private AccountRepository repository;

    public EditAccountViewModel(Application application) {
        super(application);

        repository = AccountRepository.getInstance(application.getApplicationContext());

        account = repository.getMainAccount();
    }

    public Account getAccount() {
        return editedAccount;
    }

    public void setAccount(Account account) {
        editedAccount = account;
    }

    public void saveAccount() {
        if (account == null) {
            repository.saveAccount(editedAccount);
        } else {
            repository.updateAccount(editedAccount);
        }
    }
}
