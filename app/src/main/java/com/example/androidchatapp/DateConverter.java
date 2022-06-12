package com.example.androidchatapp;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static LocalDateTime toDate(Long dateLong){
        return dateLong == null ? null: new (dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
