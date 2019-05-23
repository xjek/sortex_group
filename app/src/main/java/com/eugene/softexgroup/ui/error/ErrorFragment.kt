package com.eugene.softexgroup.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eugene.softexgroup.R
import com.eugene.softexgroup.injection.KoinApplication
import com.eugene.softexgroup.ui.ListScreen
import kotlinx.android.synthetic.main.fragment_no_connection.view.*

/**
 * Экран, показывающий пользователю информацию об ошибки
 */
class ErrorFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_no_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val errorType = arguments?.getString("errorType") ?: ""
        view.message.text = getString(if (errorType == ErrorType.NO_INTERNET_CONNECTION.name)
            R.string.no_internet_connection else R.string.default_app_error)

        view.button.setOnClickListener {
            KoinApplication.instance.router.navigateTo(ListScreen())
        }
    }
}

/**
 * Создание ErrorFragment
 * @param errorType Тип ошибки
 */
fun createErrorFragment(errorType: ErrorType) = ErrorFragment().apply {
    val bundle = Bundle()
    bundle.putString("errorType", errorType.name)
    arguments = bundle
}