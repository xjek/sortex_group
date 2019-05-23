package com.eugene.softexgroup.ui

import androidx.fragment.app.Fragment
import com.eugene.softexgroup.ui.error.ErrorType
import com.eugene.softexgroup.ui.error.createErrorFragment
import com.eugene.softexgroup.ui.list.ListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Файл, содержащий реализации экранов для cicerone
 */


/**
 * Экран со списком сообщений
 */
class ListScreen: SupportAppScreen() {
    override fun getFragment(): Fragment {
        return ListFragment()
    }
}

/**
 * Экран с сообщением отсутсвия интернета
 */
class NoConnectionScreen: SupportAppScreen() {
    override fun getFragment(): Fragment {
        return createErrorFragment(ErrorType.NO_INTERNET_CONNECTION)
    }
}

/**
 * Экран с сообщением о том, что произошла ошибка
 */
class ErrorScreen: SupportAppScreen() {
    override fun getFragment(): Fragment {
        return createErrorFragment(ErrorType.DEFAULT_ERROR)
    }
}