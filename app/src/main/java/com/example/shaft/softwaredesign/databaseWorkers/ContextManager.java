package com.example.shaft.softwaredesign.databaseWorkers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.shaft.softwaredesign.model.Account;

import java.lang.ref.WeakReference;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContextManager {

    private static ContextManager instance;

    private Database database;

    private MutableLiveData<Account> account = new MutableLiveData<>();

    private ContextManager(Context context) {
        database = Database.getInstance(context);
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
            account.setValue(Extensions.castToExternal(database.getAccountManager().loadAccount(id)));
        }
        return account;
    }

    public void saveAccount(Account account) {
        new ContextManager.ChangeDatabase(() -> {
            database.getAccountManager().insertAccount(Extensions.castToDatadase(account));
        }).execute();
    }

    public void updateAccount(Account account) {
        new ContextManager.ChangeDatabase(() -> {
            database.getAccountManager().updateAccount(Extensions.castToDatadase(account));
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
