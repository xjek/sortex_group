package com.eugene.softexgroup.repository.remote.api

import com.eugene.softexgroup.model.Message
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Сервис retrofit
 */
interface WebService {

    @GET("Softex-Group/task-mobile/master/test.json")
    fun getMessages(): Observable<MutableList<Message>>
}