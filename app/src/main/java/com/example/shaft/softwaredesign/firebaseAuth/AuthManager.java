package com.example.shaft.softwaredesign.firebaseAuth;

import com.example.shaft.softwaredesign.firebaseAuth.state.SignInState;
import com.example.shaft.softwaredesign.firebaseAuth.state.SignUpState;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthManager {
    private MutableLiveData<SignInState> signInState = new MutableLiveData<>();
    private MutableLiveData<SignUpState> signUpState = new MutableLiveData<>();

    private static AuthManager instance;
    private AuthProvider provider;

    private AuthManager(){
        provider = new AuthProvider();
    }

    public static AuthManager getInstance(){
        if (instance == null){
            instance = new AuthManager();
        }

        return instance;
    }

    public boolean isSignIn() {
        return provider.isSignIn();
    }

    public String getCurrentUserId() {
        return provider.getCurrentUserId();
    }

    public String getCurrentUserEmail() {
        return provider.getCurrentUserEmail();
    }

    public LiveData<SignInState> signInUser(String email, String password) {
        provider.signInUser(email, password, new AuthListener() {
            @Override
            public void onSuccess() {
                SignInState state = new SignInState();
                state.isSuccess = true;
                signInState.setValue(state);
            }

            @Override
            public void onError(String msg) {
                SignInState state = new SignInState();
                state.error = msg;
                signInState.setValue(state);
            }

            @Override
            public void onWeakPassword() {
                // never called in sign in but we must implement it here
            }

            @Override
            public void onInvalidCredentials() {
                SignInState state = new SignInState();
                state.isInvalidCredentials = true;
                state.error = "Wrong password or email!";
                signInState.setValue(state);
            }

            @Override
            public void onUserCollision() {
                // never called in sign in but we must implement it here
            }
        });

        return signInState;
    }

    public LiveData<SignUpState> signUpUser(String email, String password) {
        provider.signUpUser(email, password, new AuthListener() {
            @Override
            public void onSuccess() {
                SignUpState state = new SignUpState();
                state.isSuccess = true;
                signUpState.setValue(state);
            }

            @Override
            public void onError(String msg) {
                SignUpState state = new SignUpState();
                state.error = msg;
                signUpState.setValue(state);
            }

            @Override
            public void onWeakPassword() {
                SignUpState state = new SignUpState();
                state.isWeakPassword = true;
                state.error = "Password too weak!";
                signUpState.setValue(state);
            }

            @Override
            public void onInvalidCredentials() {
                SignUpState state = new SignUpState();
                state.isInvalidCredentials = true;
                state.error = "Wrong password or email!";
                signUpState.setValue(state);
            }

            @Override
            public void onUserCollision() {
                SignUpState state = new SignUpState();
                state.isUserCollision = true;
                state.error = "Email already taken!";
                signUpState.setValue(state);
            }
        });

        return signUpState;
    }

    public void signOut() {
        provider.signOut();
    }
}
