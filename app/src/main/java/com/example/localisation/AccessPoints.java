package com.example.localisation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="AccessPoints")
public class AccessPoints {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int bhavi;
    private int vivo_1907;
    private int Shivaay_Prerna;
    private String roomno;

    public int getBhavi() {
        return bhavi;
    }

    public void setBhavi(int bhavi) {
        this.bhavi = bhavi;
    }

    public int getVivo_1907() {
        return vivo_1907;
    }

    public void setVivo_1907(int vivo_1907) {
        this.vivo_1907 = vivo_1907;
    }

    public int getShivaay_Prerna() {
        return Shivaay_Prerna;
    }

    public void setShivaay_Prerna(int shivaay_Prerna) {
        Shivaay_Prerna = shivaay_Prerna;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
