package com.eugene.softexgroup.repository.remote.api

import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Распределение потоков для загрузки данных
 */
class ThreadTransformer<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}

/**
 * Управление флагом загрузки данных
 */
class LoadingTransformer<T>(private var isLoading: ObservableField<Boolean>): ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        upstream.doOnSubscribe {
            isLoading.set(true)
        }
            .doOnTerminate {
                isLoading.set(false)
            }
}