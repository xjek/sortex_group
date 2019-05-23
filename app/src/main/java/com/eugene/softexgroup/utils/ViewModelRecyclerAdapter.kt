package com.eugene.softexgroup.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * Абстрактный адапте для recycler view
 */
abstract class ViewModelRecyclerAdapter: RecyclerView.Adapter<ViewModelRecyclerAdapter.ViewHolder>() {

    /**
     * Держатель view с data binding
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
    }

    /**
     * Объекты, которыми можно поделиться с ячейками
     */
    private val sharedObjects = Hashtable<Int, Any>()

    /**
     * Класс с информацией об ячейке
     */
    data class CellInfo(val layoutId: Int, val bindingId: Int)

    /**
     * Элементы списка
     */
    protected val items = ArrayList<Any>()

    /**
     * Словарь с типами ячеек
     */
    private val cellMap = Hashtable<Class<out Any>, CellInfo>()

    /**
     * Добавить ячейку
     * @param clazz тип модели
     * @param layoutId id разметки
     * @param bindingId id связывания из разметки
     */
    protected fun addCell(clazz: Class<out Any>, @LayoutRes layoutId: Int, bindingId: Int) {
        cellMap[clazz] = CellInfo(layoutId, bindingId)
    }

    /**
     * Получить информацию о очейки
     * @param viewModel модель
     */
    private fun getCellInfo(viewModel: Any): CellInfo {
        cellMap.entries
            .filter { it.key == viewModel.javaClass }
            .first { return it.value }

        throw Exception("Cell info for class ${viewModel.javaClass.name} not found.")
    }

    /**
     * Колво элементов
     */
    override fun getItemCount(): Int = items.size

    /**
     * Получить тип ячейки
     */
    override fun getItemViewType(position: Int): Int {
        return getCellInfo(items[position]).layoutId
    }

    /**
     * Присоединение view holder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(items[position])
        if (cellInfo.bindingId != 0)
            holder.binding?.setVariable(cellInfo.bindingId, items[position])
    }

    /**
     * Поделиться объектом с ячейкой
     */
    protected fun addSharedObject(sharedObject: Any, bindingId: Int) {
        sharedObjects[bindingId] = sharedObject
    }

    /**
     * Создание view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        val viewHolder = ViewHolder(view)

        sharedObjects.forEach { viewHolder.binding?.setVariable(it.key, it.value) }

        return viewHolder
    }

    /**
     * Добавить элементы в список
     */
    fun add(items: List<Any>) {
        val itemCount = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(if (itemCount == 0) 0 else itemCount - 1, items.size)
    }
}