package com.example.androidchatapp;

public class Token {
    public String firebaseToken;
    public String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Token(String firebaseToken, String userId) {
        this.firebaseToken = firebaseToken;
        this.userId = userId;
    }

    public String getToken() {
        return firebaseToken;
    }

    public void setToken(String token) {
        this.firebaseToken = token;
    }
}

