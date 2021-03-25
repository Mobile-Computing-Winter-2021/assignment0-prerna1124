package com.example.sensors;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TempDao {

    @Insert
    void insert(Temperature t) ;

    @Query("delete from Temperature")
    void delete();

}
