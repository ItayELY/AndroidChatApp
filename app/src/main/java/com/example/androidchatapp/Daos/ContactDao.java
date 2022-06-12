package com.example.androidchatapp.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;

import java.util.List;
@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact ")
    List<Contact> index();
    @Query("SELECT * FROM contact WHERE userId = :userId AND id = :name")
    Contact get(String userId, String name);

    @Query("SELECT * FROM contact WHERE userId = :userId")
    List<Contact> getAll(String userId);

    @Insert
    void insert(Contact... contacts);
    @Delete
    void delete(Contact... contacts);
    @Update
    void update(Contact... contacts);
}
