package com.eugene.softexgroup.injection

import com.eugene.softexgroup.BuildConfig
import com.eugene.softexgroup.repository.local.db.AppDataBase
import com.eugene.softexgroup.repository.local.db.getDataBase
import com.eugene.softexgroup.repository.remote.RemoteRepository
import com.eugene.softexgroup.repository.remote.api.ConnectionMonitor
import com.eugene.softexgroup.repository.remote.api.getGSON
import com.eugene.softexgroup.repository.remote.api.getOKHttpClient
import com.eugene.softexgroup.repository.remote.api.getWebService
import com.eugene.softexgroup.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Все модули dependency injection приложения
 *
 *
 */


/**
 * Главный модуль
 */
val mainModule = module {
    viewModel { ListViewModel(get(), get()) }
}

/**
 * Модуль для загрузки данных по сети
 */
val remoteRepositoryModule = module {
    single { ConnectionMonitor(get()) }
    single { getOKHttpClient(get()) }
    single { getGSON() }
    single { getWebService(get(), get(), BuildConfig.BASE_URL) }
    single { RemoteRepository(get()) }
}

/**
 * Модуль для загрузке локальных данных
 */
val localRepositoryModule = module {
    single { getDataBase(get()) }
    single { get<AppDataBase>().messageDao() }
}