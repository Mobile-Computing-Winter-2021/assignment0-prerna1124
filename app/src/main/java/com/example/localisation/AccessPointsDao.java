package com.example.localisation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccessPointsDao {

    @Insert
    void insert(AccessPoints ap);

    @Query("SELECT * from AccessPoints")
    List<AccessPoints> getData();

}
