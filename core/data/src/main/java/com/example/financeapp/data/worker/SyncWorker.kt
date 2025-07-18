//package com.example.financeapp.data.worker
//
//import android.Manifest
//import android.content.Context
//import androidx.annotation.RequiresPermission
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedFactory
//import dagger.assisted.AssistedInject
//import javax.inject.Inject
//
//class SyncWorker @AssistedInject constructor(
//    @Assisted context: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val syncService: SyncService
//) : CoroutineWorker(context, workerParams) {
//
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    override suspend fun doWork(): Result {
//        return try {
//            syncService.syncAllData()
//            Result.success()
//        } catch (e: Exception) {
//            Result.retry()
//        }
//    }
//
//    @AssistedFactory
//    interface Factory : WorkerAssistedFactory<SyncWorker> {
//        override fun create(
//            @Assisted context: Context,
//            @Assisted workerParams: WorkerParameters
//        ): SyncWorker
//    }
//}