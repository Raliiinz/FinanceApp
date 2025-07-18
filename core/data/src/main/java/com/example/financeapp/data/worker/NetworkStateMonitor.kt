//package com.example.financeapp.data.worker
//
//import android.Manifest
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.Network
//import androidx.annotation.RequiresPermission
//import javax.inject.Inject
//
//class NetworkStateMonitor @Inject constructor(
//    private val context: Context,
//    private val syncManager: SyncManager
//) {
//    private val connectivityManager by lazy {
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    }
//
//    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
//        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//        override fun onAvailable(network: Network) {
//            super.onAvailable(network)
//            syncManager.triggerImmediateSync()
//        }
//    }
//
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    fun register() {
//        connectivityManager.registerDefaultNetworkCallback(networkCallback)
//    }
//
//    fun unregister() {
//        connectivityManager.unregisterNetworkCallback(networkCallback)
//    }
//}