//package com.example.financeapp.data.di
//
//import android.content.Context
//import androidx.work.ListenableWorker
//import androidx.work.WorkerFactory
//import androidx.work.WorkerParameters
//import com.example.financeapp.data.worker.SyncService
//import com.example.financeapp.data.worker.SyncWorker
//import com.example.financeapp.data.worker.WorkerAssistedFactory
//import javax.inject.Provider
//import javax.inject.Inject
//
//class AppWorkerFactory @Inject constructor(
//    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<WorkerAssistedFactory<out ListenableWorker>>>
//) : WorkerFactory() {
//    override fun createWorker(
//        appContext: Context,
//        workerClassName: String,
//        workerParameters: WorkerParameters
//    ): ListenableWorker? {
//        val foundEntry = workerFactories.entries.find {
//            Class.forName(workerClassName).isAssignableFrom(it.key)
//        }
//        val factoryProvider = foundEntry?.value
//            ?: throw IllegalArgumentException("Unknown worker class: $workerClassName")
//        return factoryProvider.get().create(appContext, workerParameters)
//    }
//}