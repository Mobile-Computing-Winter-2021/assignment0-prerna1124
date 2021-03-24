package com.example.sensors;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="GPS")
public class GPS {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Latitude")
    private String lat;

    @ColumnInfo(name = "Longitude")
    private String lon;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
