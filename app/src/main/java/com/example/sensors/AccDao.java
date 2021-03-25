package com.example.sensors;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccDao {

    @Insert
    void insert(Accelerator a) ;


    @Query("Select * from Accelerometer where time >=:lastOneHourTime and time <=:currTime ")
    List<Accelerator> fetchData(long lastOneHourTime,long currTime);


    @Query("delete from Accelerometer")
    void delete();

}
