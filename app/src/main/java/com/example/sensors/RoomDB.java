package com.example.sensors;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Temperature.class,Accelerator.class,Light.class,Proximity.class,GPS.class,LinearAcc.class},version = 1,exportSchema = false)
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

    public abstract TempDao tempDao();
    public abstract ProxDao proxDao();
    public abstract LightDao lightDao();
    public abstract GPSDao gpsDao();
    public abstract AccDao accDao();
    public abstract LinAccDao linAccDao();

}
