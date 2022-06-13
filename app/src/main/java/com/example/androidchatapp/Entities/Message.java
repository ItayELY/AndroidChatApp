package com.example.androidchatapp.Entities;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidchatapp.DateConverter;

import java.text.ParseException;
import java.util.Date;
import java.time.*;
@Entity
@TypeConverters(DateConverter.class)
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int ChatId;
    private String content;
    private String created;
    private Boolean sent;
    private String sentBy;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String content, Boolean sent, String sentBy, int ChatId, String created) {
        this.content = content;

        this.created =  created;
        this.sent = sent;
        this.sentBy = sentBy;
        this.ChatId = ChatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Date getCreatedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy, hh-mm");
        try {
            Date date = sdf.parse(created);
            return date;
        }catch (Exception e){
            return null;
        }
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public int getChatId() {
        return ChatId;
    }

    public void setChatId(int chatId) {
        ChatId = chatId;
    }

    @Override
    public String toString() {
        return
                "content=" + content +
                ", sent by=" + sentBy;
    }
}
