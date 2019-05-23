package com.eugene.softexgroup.utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

/**
 * Recycler View с пагинацией
 */
class PagingRecyclerView(context: Context, attrs: AttributeSet): RecyclerView(context, attrs) {

    /**
     * Колво элементов для вывода
     */
    var limit = 0

    /**
     * Позиция последнего видимого элемента
     */
    val lastVisibleItemPosition: Int
        get() = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    /**
     * Колво элементов
     */
    val itemCount: Int
        get() = adapter!!.itemCount

    /**
     * Диапазон позиций элементов, при котором необходимо загружать новый денные
     */
    var lastRangeForLoad = 0..0

    /**
     * Слушатель загрузки новых данных
     */
    val endListEvent = PublishSubject.create<OffsetLimit>()

    init {
        initScrollListener()
    }

    /**
     * Инициализировать слутеля скролла
     * При достижении последнего видимого элемента с позицией itemCount - 1 - (limit /2) отсправляется сигнал
     * о загрузке новых данных
     */
    private fun initScrollListener() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val updatePosition = itemCount - 1 - (limit /2)
                if (lastVisibleItemPosition >= updatePosition && updatePosition !in lastRangeForLoad) {
                    lastRangeForLoad = updatePosition..itemCount
                    endListEvent.onNext(OffsetLimit(itemCount, limit))
                }
            }
        })
    }
}

/**
 * Ограничения позиций данных
 */
data class OffsetLimit(val offset: Int, val limit: Int)