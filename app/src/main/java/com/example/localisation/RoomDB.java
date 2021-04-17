package com.example.localisation;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={AccessPoints.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;

    private static String DB_NAME = "database";

    public synchronized static RoomDB getInstance(Context context) {

        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;

    }

    public abstract AccessPointsDao apDao();

}
