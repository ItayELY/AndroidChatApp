package com.example.androidchatapp.Compatible;

public class UserToApi {
    private String id;
    private String name;
    private String password;
    private String server;

    public UserToApi(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserToApi(String id, String name, String password, String server) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.server = server;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
