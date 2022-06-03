package com.example.androidchatapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;

@Database(entities = {User.class, Contact.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ContactDao contactDao();
}
