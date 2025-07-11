package com.example.financeapp

import android.app.Application
import com.example.financeapp.di.AppComponent
import com.example.financeapp.di.DaggerAppComponent

/**
 * Главный класс приложения (точка входа)
 */
class FinanceApplication : Application() {
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

    companion object {
        lateinit var instance: FinanceApplication
            private set
    }
}
