package com.eugene.softexgroup.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eugene.softexgroup.utils.APP_DATE_FORMAT
import com.eugene.softexgroup.utils.formatDate
import java.util.*

/**
 * Модель сообщений
 */
@Entity(tableName = "message")
data class Message(@PrimaryKey val id: String, val time: Date, val name: String, val image: String?) {
    fun getDate() = formatDate(time, APP_DATE_FORMAT)
}