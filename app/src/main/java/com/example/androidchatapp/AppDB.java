package com.example.androidchatapp;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;

@Database(entities = {User.class, Contact.class, Message.class, Chat.class}, version = 3
               // autoMigrations = {
               //         @AutoMigration(from = 2, to = 3)
              //  }
        )
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ContactDao contactDao();
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();
}
