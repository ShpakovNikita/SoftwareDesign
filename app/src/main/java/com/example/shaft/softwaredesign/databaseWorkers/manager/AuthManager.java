package com.example.shaft.softwaredesign.databaseWorkers.manager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.shaft.softwaredesign.MainActivity;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.ui.auth.AuthActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;

public class AuthManager {
    private static AuthManager instance;
    private static FirebaseAuth auth;

    private AuthManager(){}

    public static AuthManager getInstance(){
        if (instance == null){
            instance = new AuthManager();
            auth = FirebaseAuth.getInstance();
        }

        return instance;
    }

    public boolean isSignIn() {
        return auth.getCurrentUser() != null;
    }

    public String getCurrentUserId() {
        if (!isSignIn()) {
            return null;
        }

        FirebaseUser user = auth.getCurrentUser();
        return Objects.requireNonNull(user).getUid();
    }

    public String getCurrentUserEmail() {
        if (!isSignIn()) {
            return null;
        }

        FirebaseUser user = auth.getCurrentUser();
        return Objects.requireNonNull(user).getEmail();
    }

    public void signInUser(String email, String password, Context context, Runnable onSuccess, Runnable... runnables) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        for (Runnable runnable:
                             runnables) {
                            runnable.run();
                        }

                        // progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Toast.makeText(
                                        context,
                                        "Password too short",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(
                                        context,
                                        "Something gone wrong",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            context.finish();
                        }
                    }
                });
    }

    public void registerUser(String email, String password, AuthListener listener) {

    }

    public void signOut() {
        auth.signOut();
    }

}
