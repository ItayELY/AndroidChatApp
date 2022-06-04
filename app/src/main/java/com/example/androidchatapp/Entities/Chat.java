package com.example.androidchatapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String inter1;
    private String inter2;

    public Chat(String inter1, String inter2) {
        this.inter1 = inter1;
        this.inter2 = inter2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getInterlocuters() {
        List<String> inters= new ArrayList<>();
        inters.add(this.inter1);
        inters.add(this.inter2);
        return inters;
    }

    public String getInter1() {
        return inter1;
    }

    public void setInter1(String inter1) {
        this.inter1 = inter1;
    }

    public String getInter2() {
        return inter2;
    }

    public void setInter2(String inter2) {
        this.inter2 = inter2;
    }
}
