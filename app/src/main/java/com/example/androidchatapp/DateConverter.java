package com.example.androidchatapp;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {


    @TypeConverter
    public static Date toDate(Long dateLong){
        SimpleDateFormat sdf = new SimpleDateFormat("\"yyyy'-'MM'-'dd'T'HH':'mm':'ss.fffffffK\"");
        return dateLong == null? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
