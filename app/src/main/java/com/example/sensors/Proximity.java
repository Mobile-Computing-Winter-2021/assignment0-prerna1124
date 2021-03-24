package com.example.sensors;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Proximity")
public class Proximity {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Prox")
    private float prox;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getProx() {
        return prox;
    }

    public void setProx(float prox) {
        this.prox = prox;
    }
}
