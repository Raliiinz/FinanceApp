//package com.example.financeapp.di
//
//import android.app.Application
//import android.content.Context
//import androidx.work.Configuration
//import androidx.work.ListenableWorker
//import androidx.work.WorkManager
//import androidx.work.WorkerFactory
//import com.example.financeapp.base.di.qualifiers.ApplicationContext
//import com.example.financeapp.base.di.scopes.AppScope
//import com.example.financeapp.data.di.AppWorkerFactory
//import com.example.financeapp.data.worker.SyncService
//import com.example.financeapp.data.worker.WorkerAssistedFactory
//import dagger.Module
//import dagger.Provides
//import javax.inject.Provider
//import javax.inject.Singleton
//
//@Module
//class AppModule {
//    @Provides
//    @AppScope
//    @ApplicationContext
//    fun provideContext(application: Application): Context = application
//
//    @Provides
//    @AppScope
//    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
//        return WorkManager.getInstance(context)
//    }
//
//    @Provides
//    @AppScope
//    fun provideWorkerFactory(
//        workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<WorkerAssistedFactory<out ListenableWorker>>>
//    ): WorkerFactory {
//        return AppWorkerFactory(workerFactories)
//    }
//
//    @Provides
//    @AppScope
//    fun provideWorkManagerConfiguration(
//        workerFactory: WorkerFactory
//    ): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//    }
//}