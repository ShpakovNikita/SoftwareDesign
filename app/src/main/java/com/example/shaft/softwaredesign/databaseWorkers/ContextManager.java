package com.example.shaft.softwaredesign.databaseWorkers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.shaft.softwaredesign.databaseWorkers.Database;
import com.example.shaft.softwaredesign.databaseWorkers.Extensions;
import com.example.shaft.softwaredesign.model.Account;

import java.lang.ref.WeakReference;

public class ContextManager {

    private static ContextManager instance;

    private Database database;

    private Account account;

    private ContextManager(Context context) {
        database = Database.getInstance(context);

        account = new Account();
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

    public Account getAccount(int id) {
        if (instance.account == null) {
            account = Extensions.castToExternal(database.getAccountManager().loadAccount(id));
        }
        return account;
    }

    public Account getMainAccount() {
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
        account = _account;
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
