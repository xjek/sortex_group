package com.eugene.softexgroup.repository.local.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eugene.softexgroup.model.Message
import com.eugene.softexgroup.repository.local.MessageDao

/**
 * Инициализация базы
 */
@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(RoomDateConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}

/**
 * Получение базы
 */
fun getDataBase(context: Context): AppDataBase =
    Room.databaseBuilder(context, AppDataBase::class.java, "app-db")
        .allowMainThreadQueries()
        .build()