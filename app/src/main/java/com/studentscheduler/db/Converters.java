package com.studentscheduler.db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
