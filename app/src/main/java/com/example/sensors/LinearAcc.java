package com.example.sensors;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="LinearAcc")
public class LinearAcc {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "X")
    private float x;

    @ColumnInfo(name = "Y")
    private float y;

    @ColumnInfo(name = "Z")
    private float z;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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
