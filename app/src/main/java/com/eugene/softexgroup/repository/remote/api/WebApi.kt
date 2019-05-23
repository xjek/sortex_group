package com.eugene.softexgroup.repository.remote.api

import com.eugene.softexgroup.repository.remote.api.deserializer.DateDeserializer
import com.eugene.softexgroup.repository.remote.api.exceptions.NoInternetConnection
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Создание клиента
 */
fun getOKHttpClient(connectionMonitor: ConnectionMonitor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor {
            if (connectionMonitor.isConnected()) {
                return@addInterceptor it.proceed(it.request())
            } else {
                throw NoInternetConnection()
            }
        }
        .build()

/**
 * Создание парса
 */
fun getGSON(): Gson =
    GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .registerTypeAdapter(Date::class.java,
            DateDeserializer()
        )
        .create()

/**
 * Создание сервиса retrofit
 */
fun getWebService(okHttpClient: OkHttpClient, gson: Gson, baseUrl: String): WebService =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(WebService::class.java)

