package com.example.shaft.softwaredesign.firebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;

public class AuthProvider {
    private FirebaseAuth auth;

    public AuthProvider(){
        auth = FirebaseAuth.getInstance();
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

    public void signInUser(String email, String password, AuthListener listener) {
        Task<AuthResult> task = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
        setOnTaskCompleteListener(task, listener);
    }

    public void signUpUser(String email, String password, AuthListener listener) {
        Task<AuthResult> task = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password);
        setOnTaskCompleteListener(task, listener);
    }

    private void setOnTaskCompleteListener(Task<AuthResult> task, AuthListener listener) {
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (listener == null) {
                    return;
                }

                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        listener.onWeakPassword();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        listener.onInvalidCredentials();
                    } catch(FirebaseAuthUserCollisionException e) {
                        listener.onUserCollision();
                    } catch (Exception e) {
                        listener.onError(e.getMessage());
                    }
                }
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }

}
