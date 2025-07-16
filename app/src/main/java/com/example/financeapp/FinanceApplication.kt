package com.example.financeapp

import android.app.Application
import com.example.financeapp.di.AppComponent
import com.example.financeapp.di.DaggerAppComponent
import com.example.financeapp.transaction.di.TransactionComponent
import com.example.financeapp.transaction.di.TransactionComponentProvider

/**
 * Главный класс приложения (точка входа)
 */
class FinanceApplication : Application(), TransactionComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)

        instance = this
    }

    override fun transactionComponent(): TransactionComponent {
        return appComponent.transactionComponentBuilder().build()
    }

    companion object {
        lateinit var instance: FinanceApplication
            private set
    }
}
