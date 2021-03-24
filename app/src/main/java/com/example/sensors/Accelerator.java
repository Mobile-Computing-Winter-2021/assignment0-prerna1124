package com.example.sensors;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="Accelerometer")
public class Accelerator {

    @PrimaryKey
    private long ID;

    @ColumnInfo(name = "X")
    private float x;

    @ColumnInfo(name = "Y")
    private float y;

    @ColumnInfo(name = "Z")
    private float z;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
