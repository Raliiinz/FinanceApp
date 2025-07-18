//package com.example.financeapp.data.di
//
//import androidx.work.ListenableWorker
//import com.example.financeapp.data.worker.SyncWorker
//import com.example.financeapp.data.worker.WorkerAssistedFactory
//import dagger.Binds
//import dagger.Module
//import dagger.multibindings.IntoMap
//
//@Module
//abstract class WorkerBindingModule {
//    @Binds
//    @IntoMap
//    @WorkerKey(SyncWorker::class)
//    abstract fun bindSyncWorkerFactory(factory: SyncWorker.Factory): WorkerAssistedFactory<out ListenableWorker>
//}