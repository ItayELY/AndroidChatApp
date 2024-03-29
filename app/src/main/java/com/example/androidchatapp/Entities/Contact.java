package com.example.androidchatapp.Entities;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity

public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int IId;
    private String userId;
    private String id;
    private String name;
    private String server;
    private String lastMessageContent;
    private String lastMessageDate;

    public int getIId() {
        return IId;
    }

    public void setIId(int IId) {
        this.IId = IId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Contact(String userId, String id, String name, String server, String lastMessageContent,
                   String lastMessageDate) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.server = server;
        this.lastMessageContent = lastMessageContent;
        this.lastMessageDate = lastMessageDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastMessage='" + lastMessageContent + '\'' +
                '}';
    }
      public String getLastMessageDate() {
        return lastMessageDate;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Date getLastMessageDateD(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy, hh-mm");
        try {
            Date date = sdf.parse(this.lastMessageDate);
            return date;
        }catch (Exception e){
            return null;
        }
    }
    public void setLastMessageDate(String lastMessageDate) {
        lastMessageDate = lastMessageDate;
    }

}
