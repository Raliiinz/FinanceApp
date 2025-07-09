package com.example.financeapp

import android.app.Application
import com.example.financeapp.di.AppComponent
import com.example.financeapp.di.DaggerAppComponent

/**
 * Главный класс приложения (точка входа)
 */
class FinanceApplication : Application() {
    // Создаем AppComponent при старте приложения
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this) // Передаем контекст приложения
            .build()

        // Если есть методы inject() в AppComponent
        appComponent.inject(this)

        // Можно сохранить ссылку на Application для удобства, если это синглтон
        instance = this
    }

    companion object {
        lateinit var instance: FinanceApplication
            private set
    }
}
