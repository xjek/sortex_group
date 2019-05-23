package com.eugene.softexgroup.repository.remote.api

import android.content.Context
import android.net.ConnectivityManager

/**
 * Проверка интернет соединения
 */
class ConnectionMonitor(private val context: Context) {

    fun isConnected(): Boolean {
        val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            for (networkType in networkTypes) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                    return true
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }
}