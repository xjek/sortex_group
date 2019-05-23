package com.eugene.softexgroup.ui.list

import androidx.lifecycle.MutableLiveData
import com.eugene.softexgroup.injection.KoinApplication
import com.eugene.softexgroup.model.Message
import com.eugene.softexgroup.repository.local.MessageDao
import com.eugene.softexgroup.repository.remote.RemoteRepository
import com.eugene.softexgroup.repository.remote.api.LoadingTransformer
import com.eugene.softexgroup.repository.remote.api.ThreadTransformer
import com.eugene.softexgroup.repository.remote.api.exceptions.NoInternetConnection
import com.eugene.softexgroup.ui.ErrorScreen
import com.eugene.softexgroup.ui.NoConnectionScreen
import com.eugene.softexgroup.utils.BaseViewModel
import com.eugene.softexgroup.utils.OffsetLimit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

/**
 * ViewModel списка сообщений
 */
class ListViewModel(private val remoteRepository: RemoteRepository, private val dao: MessageDao): BaseViewModel() {

    val limit = 15

    /**
     * Все сообщения
     */
    var messages = MutableLiveData<ArrayList<Message>>()

    /**
     * Сообщения, которые загружаются при пагинации
     */
    var pagingMessages = MutableLiveData<List<Message>>()


    init {
        getMessages()
    }

    /**
     * Получение сообщений
     * @param offsetLimit Пагинация
     */
    private fun getMessages(offsetLimit: OffsetLimit = OffsetLimit(0, limit)) {

        val observable =
            if (dao.count() == 0) {
                remoteRepository.getMessages()
                    .map { dao.insert(it); return@map it.take(offsetLimit.limit) }
                    .compose(ThreadTransformer<List<Message>>())
                    .compose(LoadingTransformer<List<Message>>(isLoading))
            } else {
                dao.get(offsetLimit.offset, offsetLimit.limit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
            }

        compositeDisposable += observable
            .subscribeBy(onNext = {
                if (messages.value == null) {
                    messages.value = it as ArrayList<Message>
                } else {
                    pagingMessages.value = it
                    val allMessages = ArrayList<Message>()
                    allMessages.addAll(messages.value!!)
                    allMessages.addAll(pagingMessages.value!!)
                    messages.value = allMessages
                }
            }, onError = {
                when (it) {
                    is NoInternetConnection -> KoinApplication.instance.router.navigateTo(NoConnectionScreen())
                    else -> KoinApplication.instance.router.navigateTo(ErrorScreen())
                }
            })
    }

    /**
     * Получение сообщений
     * @param loadDataObservable Слушатель загрузки данных
     */
    fun getMessages(loadDataObservable: Observable<OffsetLimit>) {
        compositeDisposable += loadDataObservable.subscribe {
            getMessages(it)
        }
    }

    /**
     * Удалить сообщение
     * @param observable Слушатель удаления сообщения. В составе с сообщением и позицией элемента для удаления
     */
    fun removeMessage(observable: Observable<Pair<Message, Int>>) {
        compositeDisposable += observable.subscribe {
            dao.delete(it.first)
            messages.value!!.removeAt(it.second)
            getMessages(OffsetLimit(messages.value!!.size, 1))
        }
    }



}


