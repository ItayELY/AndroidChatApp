package com.example.androidchatapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidchatapp.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE id = :id")
    User get(int id);

    @Query("SELECT * FROM user WHERE userName = :username")
    User find(String username);

    @Insert
    void insert(User... users);
    @Delete
    void delete(User... users);
    @Update
    void update(User... users);
}
