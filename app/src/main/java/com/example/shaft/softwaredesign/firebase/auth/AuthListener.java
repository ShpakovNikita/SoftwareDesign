package com.example.shaft.softwaredesign.firebase.auth;

public interface AuthListener {
    void onSuccess();

    void onWeakPassword();

    void onInvalidCredentials();

    void onUserCollision();

    // For interception of each not trivial FB exception
    void onError(String msg);
}
