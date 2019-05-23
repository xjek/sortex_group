package com.eugene.softexgroup.utils

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Базовый класс ViewModel
 */
open class BaseViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    var isLoading = ObservableField(false)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
