package com.example.androidchatapp.Compatible;

public class ContactToApi {
    private String userId;
    private String id;
    private String name;
    private String server;
    private String lastMessageDate;

    public ContactToApi(String userId, String id, String name, String server, String lastMessageDate) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.server = server;
        this.lastMessageDate = lastMessageDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
