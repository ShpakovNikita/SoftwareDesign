package com.example.shaft.softwaredesign.databaseWorkers;

import android.content.Context;

import com.example.shaft.softwaredesign.databaseWorkers.entity.AccountEntity;
import com.example.shaft.softwaredesign.databaseWorkers.provider.AccountProvider;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {AccountEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    private static final String DATABASE_NAME = "Database";

    public abstract AccountProvider getAccountManager();

    public static Database getInstance(final Context context) {
        if (instance == null) {
            // TODO:
            synchronized (Database.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, Database.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
