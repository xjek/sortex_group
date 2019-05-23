package com.eugene.softexgroup.repository.local.db

import androidx.room.TypeConverter
import com.eugene.softexgroup.utils.APP_DATE_FORMAT
import com.eugene.softexgroup.utils.formatDate
import com.eugene.softexgroup.utils.parseDate
import java.util.*

class RoomDateConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun dateToString(date: Date) = formatDate(date, APP_DATE_FORMAT)

        @TypeConverter
        @JvmStatic
        fun stringToDate(date: String) = parseDate(date, APP_DATE_FORMAT)
    }
}