package com.example.androidchatapp.Entities;

import java.time.LocalDateTime;

public class Contact {
    private String id;
    private String name;
    private String server;
    private String lastMessage;
    private LocalDateTime LastMessageDate;

    public Contact(String id, String name, String server, String lastMessage, LocalDateTime lastMessageDate) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.lastMessage = lastMessage;
        LastMessageDate = lastMessageDate;
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

    public LocalDateTime getLastMessageDate() {
        return LastMessageDate;
    }

    public void setLastMessageDate(LocalDateTime lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }
}
