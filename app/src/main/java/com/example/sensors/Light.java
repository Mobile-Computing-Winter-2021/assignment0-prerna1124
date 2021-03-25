package com.example.sensors;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Light")
public class Light {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "time")
    private long time;

    @ColumnInfo(name = "illumination")
    private float ill;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getIll() {
        return ill;
    }

    public void setIll(float ill) {
        this.ill = ill;
    }
}
