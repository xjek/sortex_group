package com.eugene.softexgroup.repository.remote

import com.eugene.softexgroup.model.Message
import com.eugene.softexgroup.repository.remote.api.ThreadTransformer
import com.eugene.softexgroup.repository.remote.api.WebService
import io.reactivex.Observable

/**
 * Удаленный репозиторий
 */
class RemoteRepository(private val webService: WebService) {

    fun getMessages(): Observable<MutableList<Message>> =
        webService.getMessages()

}