package com.example.financeapp.network.util

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.financeapp.base.di.qualifiers.ApplicationContext
import com.example.financeapp.base.di.scopes.AppScope
import javax.inject.Inject
import androidx.annotation.RequiresPermission

/**
 * Проверяет состояние сетевого подключения.
 *
 * Ответственность: Определение доступности интернет-соединения.
 */
@AppScope
class NetworkChecker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}
