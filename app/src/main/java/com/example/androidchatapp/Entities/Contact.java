package com.example.androidchatapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
@Entity

public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int IId;
    private String userId;
    private String id;
    private String name;
    private String server;
    private String lastMessage;
   // private LocalDateTime LastMessageDate;

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

    public Contact(String id, String name, String server, String lastMessage) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.lastMessage = lastMessage;
       //LastMessageDate = lastMessageDate;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

  //  public LocalDateTime getLastMessageDate() {
   //     return LastMessageDate;
   // }

   // public void setLastMessageDate(LocalDateTime lastMessageDate) {
   //     LastMessageDate = lastMessageDate;
 //   }
}
