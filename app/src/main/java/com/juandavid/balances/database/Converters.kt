package com.juandavid.balances.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return Instant.ofEpochMilli(value?:-1).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay()?.toEpochSecond(ZoneOffset.ofHours(5))
    }
}