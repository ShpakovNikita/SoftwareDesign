package com.example.shaft.softwaredesign.contextManagers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AccountManager {

    @Query("SELECT * FROM accounts")
    List<AccountEntity> loadAccounts();

    @Query("SELECT * FROM accounts WHERE id=:id")
    AccountEntity loadAccount(int id);

    @Query("SELECT * FROM accounts WHERE main!=0")
    AccountEntity loadMainAccount();

    @Insert
    void insertAccount(AccountEntity entity);

    @Update
    void updateAccount(AccountEntity entity);

    @Delete
    void deleteAccount(AccountEntity entity);

}
