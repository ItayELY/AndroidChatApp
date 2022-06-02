package com.example.androidchatapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<String> interlocuters;
    private List<Message> messages;

    public Chat(List<String> interlocuters, List<Message> messages) {
        this.interlocuters = interlocuters;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getInterlocuters() {
        return interlocuters;
    }

    public void setInterlocuters(List<String> interlocuters) {
        this.interlocuters = interlocuters;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
