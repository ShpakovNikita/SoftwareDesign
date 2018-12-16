package com.example.shaft.softwaredesign.databaseWorkers.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.shaft.softwaredesign.databaseWorkers.Database;
import com.example.shaft.softwaredesign.databaseWorkers.extensions.AccountExtensions;
import com.example.shaft.softwaredesign.model.Account;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContextManager {

    private static FirebaseAuth auth;
    private static ContextManager instance;

    private Database database;

    private MutableLiveData<Account> account = new MutableLiveData<>();

    private ContextManager(Context context) {
        database = Database.getInstance(context);
        auth = FirebaseAuth.getInstance();
        setData(new Account());

    }


    public LiveData<Account> getData() {
        return account;
    }

    public void setData(Account data){
        account.postValue(data);
    }

    public static ContextManager getInstance(Context context) {
        if (instance == null) {
            // TODO
            synchronized (ContextManager.class) {
                if (instance == null) {
                    instance = new ContextManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LiveData<Account> getAccount(int id) {
        if (instance.account == null) {
            account.setValue(AccountExtensions.castToExternal(database.getAccountManager().loadAccount(id)));
        }
        return account;
    }

    public void saveAccount(Account account) {
        new ContextManager.ChangeDatabase(() -> {
            database.getAccountManager().insertAccount(AccountExtensions.castToDatadase(account));
        }).execute();
    }

    public void updateAccount(Account account) {
        new ContextManager.ChangeDatabase(() -> {
            database.getAccountManager().updateAccount(AccountExtensions.castToDatadase(account));
        }).execute();
    }

    // TODO
    public void setAccount(Account _account){
        account.setValue(_account);
    }

    // TODO
    private static class ChangeDatabase extends AsyncTask<Void, Void, Void> {

        private WeakReference<ContextManager.ChangeDatabase.ChangeAction> mChangeActionWeakReference;

        public interface ChangeAction {
            void change();
        }

        ChangeDatabase(ContextManager.ChangeDatabase.ChangeAction action) {
            mChangeActionWeakReference = new WeakReference<>(action);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mChangeActionWeakReference != null) {
                mChangeActionWeakReference.get().change();
            }
            return null;
        }
    }
}
