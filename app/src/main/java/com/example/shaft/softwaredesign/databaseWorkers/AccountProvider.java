package com.example.shaft.softwaredesign.databaseWorkers;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AccountProvider {

    @Query("SELECT * FROM accounts")
    List<AccountEntity> loadAccounts();

    @Query("SELECT * FROM accounts WHERE id=:id")
    AccountEntity loadAccount(int id);

    @Insert
    void insertAccount(AccountEntity entity);

    @Update
    void updateAccount(AccountEntity entity);

    @Delete
    void deleteAccount(AccountEntity entity);

}
