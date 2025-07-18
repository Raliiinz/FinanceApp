//package com.example.financeapp.data.worker
//
//import android.Manifest
//import androidx.annotation.RequiresPermission
//import androidx.work.Constraints
//import androidx.work.ExistingPeriodicWorkPolicy
//import androidx.work.NetworkType
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.PeriodicWorkRequestBuilder
//import androidx.work.WorkManager
//import com.example.financeapp.network.util.NetworkChecker
//import java.util.concurrent.TimeUnit
//import javax.inject.Inject
//
//class SyncManager @Inject constructor(
//    private val workManager: WorkManager,
//    private val networkChecker: NetworkChecker
//) {
//    private val syncConstraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .build()
//
//    fun schedulePeriodicSync() {
//        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
//            repeatInterval = 2,
//            repeatIntervalTimeUnit = TimeUnit.HOURS
//        )
//            .setConstraints(syncConstraints)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            "periodic_sync",
//            ExistingPeriodicWorkPolicy.KEEP,
//            syncRequest
//        )
//    }
//
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    fun triggerImmediateSync() {
//        if (!networkChecker.isNetworkAvailable()) return
//
//        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
//            .setConstraints(syncConstraints)
//            .build()
//
//        workManager.enqueue(syncRequest)
//    }
//}