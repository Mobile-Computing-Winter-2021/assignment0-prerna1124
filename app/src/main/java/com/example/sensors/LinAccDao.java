package com.example.sensors;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LinAccDao {

    @Insert
    void insert(LinearAcc l) ;


    @Query("Select * from LinearAcc")
    List<LinearAcc> data();


    @Query("delete from LinearAcc")
    void delete();

}
