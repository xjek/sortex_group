package com.eugene.softexgroup.ui.list

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eugene.softexgroup.R
import com.eugene.softexgroup.model.Message
import com.eugene.softexgroup.utils.ViewModelRecyclerAdapter
import io.reactivex.subjects.PublishSubject

/**
 * Адаптер для спика с сообщениями
 */
class ListAdapter: ViewModelRecyclerAdapter() {

    /**
     * Слушатель свайпа
     */
    val swipeEvent = PublishSubject.create<Pair<Message, Int>>()

    init {
        addCell(Message::class.java, R.layout.item_message, BR.message)
        addSharedObject(this, BR.adapter)
    }

    /**
     * Установить слушателя свайпа
     */
    fun setSwipe(recyclerView: RecyclerView) {
        val swipeCallback = SwipeCallback {
            val item = items[it] as Message
            swipeEvent.onNext(Pair(item, it))
            items.removeAt(it)
            notifyItemRemoved(it)

        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /**
     * Слушатель взаимодействия с элементами списка
     */
    class SwipeCallback(private val callback: (Int) -> Unit): ItemTouchHelper.Callback() {
        /**
         * Получение флагов взаимодействия
         */
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = true

        /**
         * При свайпе вызывается callback
         */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            callback(viewHolder.adapterPosition)
        }
    }
}

