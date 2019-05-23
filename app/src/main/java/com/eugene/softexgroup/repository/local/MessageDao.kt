package com.eugene.softexgroup.repository.local

import androidx.room.*
import com.eugene.softexgroup.model.Message
import io.reactivex.Single

/**
 * Работа с таблицой message в базе данных
 */
@Dao
interface MessageDao {

    /**
     * Удалить элемент
     */
    @Delete
    fun delete(message: Message)

    /**
     * Вставка элементов
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: List<Message>)

    /**
     * Выборка по позиции и с ограничением
     */
    @Query("SELECT * FROM message LIMIT :limit OFFSET :offset")
    fun get(offset: Int, limit: Int): Single<List<Message>>

    /**
     * Колво элементов
     */
    @Query("SELECT COUNT(*) FROM message")
    fun count(): Int

    /**
     * Очистка таблицы
     */
    @Query("delete from message")
    fun clear()

}