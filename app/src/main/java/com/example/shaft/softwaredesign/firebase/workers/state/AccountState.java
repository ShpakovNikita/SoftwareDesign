package com.example.shaft.softwaredesign.firebase.workers.state;

import com.example.shaft.softwaredesign.model.Account;

public class AccountState {
    public Account data;
    public boolean isSuccess;
    public String error;

    public boolean isDisconnected;
    public boolean isNetworkErrorOccurred;
}