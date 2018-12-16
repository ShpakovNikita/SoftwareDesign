package com.example.shaft.softwaredesign.firebaseAuth;

public interface AuthListener {
    void onSuccess();

    void onWeakPassword();

    void onInvalidCredentials();

    void onUserCollision();

    // For interception of each not trivial FB exception
    void onError(String msg);
}
