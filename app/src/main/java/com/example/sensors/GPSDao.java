package com.example.sensors;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GPSDao {

    @Insert
    void insert(GPS g) ;


    @Query("Select * from GPS")
    List<GPS> data();


    @Query("delete from GPS")
    void delete();

}
