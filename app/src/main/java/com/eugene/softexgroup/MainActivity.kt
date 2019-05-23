package com.eugene.softexgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eugene.softexgroup.injection.KoinApplication
import com.eugene.softexgroup.ui.list.ListFragment
import ru.terrakok.cicerone.android.support.SupportAppNavigator

/**
 * Главная автивность
 */
class MainActivity : AppCompatActivity() {

    /**
     * Навигатор cicerone
     */
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.fragment_container)

    /**
     * Создание фрагмета со списком
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = ListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        KoinApplication.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        KoinApplication.instance.navigatorHolder.removeNavigator()
    }


}
