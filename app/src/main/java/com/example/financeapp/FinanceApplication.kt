package com.example.financeapp

import android.app.Application
import androidx.work.Configuration
import com.example.financeapp.di.AppComponent
import com.example.financeapp.di.DaggerAppComponent
import com.example.financeapp.transaction.di.TransactionComponent
import com.example.financeapp.transaction.di.TransactionComponentProvider
import javax.inject.Inject

/**
 * Главный класс приложения (точка входа)
 */
class FinanceApplication : Application(), TransactionComponentProvider {
    lateinit var appComponent: AppComponent

//    @Inject
//    lateinit var workerFactory: WorkerFactory
//
//    @Inject
//    lateinit var syncManager: SyncManager
//
//    @Inject
//    lateinit var networkStateMonitor: NetworkStateMonitor

    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.factory()
            .create(this)

        appComponent.inject(this)

        instance = this

//        syncManager.schedulePeriodicSync()
//        networkStateMonitor.register()
    }

    override fun transactionComponent(): TransactionComponent {
        return appComponent.transactionComponentBuilder().build()
    }

//    override val workManagerConfiguration: Configuration
//        get() = Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()

    companion object {
        lateinit var instance: FinanceApplication
            private set
    }
}
