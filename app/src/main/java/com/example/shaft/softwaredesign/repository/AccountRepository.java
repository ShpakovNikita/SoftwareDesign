package com.example.shaft.softwaredesign.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.example.shaft.softwaredesign.contextManagers.Database;
import com.example.shaft.softwaredesign.contextManagers.Extensions;
import com.example.shaft.softwaredesign.contextManagers.AccountEntity;
import com.example.shaft.softwaredesign.model.Account;

import java.lang.ref.WeakReference;

import androidx.lifecycle.Transformations;

public class AccountRepository {

    private static AccountRepository instance;

    private Database database;

    private Account mainAccount;

    private AccountRepository(Context context) {
        database = Database.getInstance(context);

        mainAccount = Extensions.castToExternal(
                Database.getInstance(context).getAccountManager().loadMainAccount());
    }

    public static AccountRepository getInstance(Context context) {
        if (instance == null) {
            // TODO
            synchronized (AccountRepository.class) {
                if (instance == null) {
                    instance = new AccountRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public Account getAccount(int id) {
        return Extensions.castToExternal(database.getAccountManager().loadAccount(id));
    }

    public Account getMainAccount() {
        return mainAccount;
    }

    public void saveAccount(Account account) {
        new ChangeDatabase(() -> {
            database.getAccountManager().insertAccount(Extensions.castToDatadase(account));
        }).execute();
    }

    public void updateAccount(Account account) {
        new ChangeDatabase(() -> {
            database.getAccountManager().updateAccount(Extensions.castToDatadase(account));
        }).execute();
    }

    // TODO
    private static class ChangeDatabase extends AsyncTask<Void, Void, Void> {

        private WeakReference<ChangeAction> mChangeActionWeakReference;

        public interface ChangeAction {
            void change();
        }

        ChangeDatabase(ChangeAction action) {
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
