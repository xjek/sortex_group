package com.eugene.softexgroup.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

/**
 * Настройка глобальных компонентов приложения
 */
class KoinApplication: Application() {

    companion object {
        lateinit var instance: KoinApplication
    }

    private lateinit var cicerone: Cicerone<Router>

    val navigatorHolder: NavigatorHolder
        get() = cicerone.navigatorHolder

    val router: Router
        get() = cicerone.router

    /**
     * Загрузка Koin
     */
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@KoinApplication)
            modules(remoteRepositoryModule, localRepositoryModule, mainModule)
        }

        cicerone = Cicerone.create()
    }
}
