package com.example.financeapp.di

import android.app.Application
import android.content.Context
import com.example.financeapp.FinanceApplication
import com.example.financeapp.articles.di.CategoryComponent
import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.di.DataBinderModule
import com.example.financeapp.base.di.qualifiers.ApplicationContext
import com.example.financeapp.check.di.AccountComponent
import com.example.financeapp.domain.di.DomainModule
import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.expenses.di.ExpensesComponent
import com.example.financeapp.history.di.HistoryComponent
import com.example.financeapp.income.di.IncomeComponent
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.network.di.NetworkModule
import com.example.financeapp.settings.di.SettingsComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

@AppScope
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class, // Из core:network
        DataBinderModule::class, // Из core:data
        DomainModule::class,    // Из core:domain
        NavigationModule::class
    ]
)
interface AppComponent {

    // Метод для инъекции в Application класс, если нужно
    fun inject(application: FinanceApplication)

    fun expensesComponentBuilder(): ExpensesComponent.Builder
    fun checkComponentBuilder(): AccountComponent.Builder
    fun articlesComponentBuilder(): CategoryComponent.Builder
    fun historyComponentBuilder(): HistoryComponent.Builder
    fun incomeComponentBuilder(): IncomeComponent.Builder
    fun settingsComponentBuilder(): SettingsComponent.Builder

    fun historyNavigation(): HistoryNavigation
//    @Provides
//    @AppScope
//    fun provideHistoryNavigation(): HistoryNavigation {
//        return HistoryNavigation() // Или ваш реальный конструктор
//    }
    // Метод для получения ApplicationContext, доступный для зависимых компонентов
    fun application(): Application

    @ApplicationContext
    fun applicationContext(): Context

    @IoDispatchers
    fun ioDispatcher(): CoroutineDispatcher

    // -- Методы для expose зависимостей, которые будут нужны FeatureComponents --
    // Эти методы будут использоваться в Component Dependencies
    // Пример:
    // fun yourApiService(): YourApiService
    // fun yourDao(): YourDao
    // fun yourRepository(): YourRepository
    // fun yourUseCase(): YourUseCase

    // Компонент-билдер для создания AppComponent с BindsInstance
    @Component.Builder
    interface Builder {
        @BindsInstance // Используем для предоставления ApplicationContext
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}