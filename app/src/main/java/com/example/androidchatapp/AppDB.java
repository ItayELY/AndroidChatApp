package com.example.androidchatapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidchatapp.Entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
}
