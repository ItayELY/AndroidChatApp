package com.example.androidchatapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;

import java.util.List;

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message WHERE chatid = :chatid")
    List<Message> getAllMessages(int chatid);

    @Query("SELECT * FROM Message WHERE id = :id")
    Message find(int id);

    @Insert
    void insert(Message... messages);
    @Delete
    void delete(Message... messages);
    @Update
    void update(Message... messages);
}
