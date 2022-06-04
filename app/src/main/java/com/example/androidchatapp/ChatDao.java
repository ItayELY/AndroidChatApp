package com.example.androidchatapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.User;

import java.util.List;
@Dao
public interface ChatDao {
    @Query("SELECT * FROM chat")
    List<Chat> index();

    @Query("SELECT * FROM chat WHERE id = :id")
    Chat get(int id);

    @Query("SELECT id FROM chat WHERE (inter1 = :username1 OR inter2 = :username1) AND (inter1 = :username2 OR inter2 = :username2)")
    Chat find(String username1, String username2);

    @Insert
    void insert(Chat... chats);
    @Delete
    void delete(Chat... chats);
    @Update
    void update(Chat... chats);
}
