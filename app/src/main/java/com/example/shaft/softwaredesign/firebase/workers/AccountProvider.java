package com.example.shaft.softwaredesign.firebase.workers;


import com.example.shaft.softwaredesign.firebase.workers.AccountListeners;
import com.example.shaft.softwaredesign.firebase.workers.entity.FirebaseAccount;
import com.example.shaft.softwaredesign.firebase.workers.extensions.AccountExtensions;
import com.example.shaft.softwaredesign.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AccountProvider {
    private String USER_ROOT_NAME = "Users";
    private FirebaseDatabase database;

    public AccountProvider(){
        database = FirebaseDatabase.getInstance();
    }

    public void getUserById(String userId, AccountListeners.LoadAccountListener listener) {
        DatabaseReference userReference = database.getReference(USER_ROOT_NAME).child(userId);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (listener == null) {
                    return;
                }

                FirebaseAccount firebaseAccount = dataSnapshot.getValue(FirebaseAccount.class);
                Account account = AccountExtensions.castToExternal(firebaseAccount);
                if (account != null) {
                    account.setId(userId);
                }

                listener.onSuccess(account);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (listener == null) {
                    return;
                } else if (databaseError.getCode() == DatabaseError.NETWORK_ERROR) {
                    listener.onNetworkError();
                } else {
                    listener.onError(databaseError.toException().getMessage());
                }
            }
        });
    }

    public void saveAccount(FirebaseAccount firebaseAccount, AccountListeners.SaveAccountListener listener) {
        DatabaseReference userReference = database.getReference(USER_ROOT_NAME);
        String newUserId = userReference.push().getKey();

        saveAccountById(userReference, newUserId, firebaseAccount, listener);
    }

    public void saveAccount(String userId, FirebaseAccount firebaseAccount, AccountListeners.SaveAccountListener listener) {
        DatabaseReference userReference = database.getReference(USER_ROOT_NAME);

        saveAccountById(userReference, userId, firebaseAccount, listener);
    }

    private void saveAccountById(DatabaseReference userReference, String userId,
                                 FirebaseAccount firebaseAccount, AccountListeners.SaveAccountListener listener) {
        userReference = userReference.child(userId);
        Task<Void> task = userReference.setValue(firebaseAccount);

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (listener == null) {
                    return;
                }

                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseNetworkException error) {
                        listener.onNetworkError();
                    } catch (Exception e) {
                        listener.onError(e.getMessage());
                    }
                }
            }
        });
    }
}
