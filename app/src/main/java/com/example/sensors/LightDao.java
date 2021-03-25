package com.example.sensors;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LightDao {

    @Insert
    void insert(Light l) ;


    @Query("Select * from Light where time >=:lastOneHourTime and time <=:currTime ")
    List<Light> data(long lastOneHourTime,long currTime);


    @Query("delete from Light")
    void delete();

}
