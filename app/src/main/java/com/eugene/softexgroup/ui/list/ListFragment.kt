package com.eugene.softexgroup.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene.softexgroup.R
import com.eugene.softexgroup.databinding.FragmentListBinding
import com.eugene.softexgroup.model.Message
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент, содержащий список сообщений
 */
class ListFragment: Fragment() {

    private val listViewModel by viewModel<ListViewModel>()
    private lateinit var binding: FragmentListBinding

    private lateinit var adapter: ListAdapter

    /**
     * Инициализация отображения
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.viewModel = listViewModel
        return binding.root
    }

    /**
     * Работа с содержимым
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.limit = listViewModel.limit

        adapter = ListAdapter()
        adapter.setSwipe(binding.recyclerView)

        listViewModel.removeMessage(adapter.swipeEvent)
        listViewModel.getMessages(recyclerView.endListEvent)

        binding.recyclerView.adapter = adapter

        listViewModel.messages.observe(this, StartObserver())

        listViewModel

        binding.executePendingBindings()
    }

    /**
     * Слушатель для полчучения всех сообщений
     */
    inner class StartObserver: Observer<List<Message>> {
        override fun onChanged(t: List<Message>) {
            adapter.add(t)
            listViewModel.messages.removeObserver(this)
            listViewModel.pagingMessages.observe(this@ListFragment, PagingObservable())
        }
    }

    /**
     * Слушатель для получения сообщений, загруженных при пагинации
     */
    inner class PagingObservable: Observer<List<Message>> {
        override fun onChanged(t: List<Message>) {
            adapter.add(t)
        }
    }
}
