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
import com.example.financeapp.transaction.di.TransactionComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Главный компонент приложения, предоставляющий зависимости в масштабе всего приложения.
 *
 * Включает модули: NetworkModule, DataBinderModule, DomainModule и NavigationModule.
 * Предоставляет билдеры для субкомпонентов (Expenses, Account, Category и др.),
 * а также основные зависимости: Application, Context и CoroutineDispatcher.
 */
@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DataBinderModule::class,
        DomainModule::class,
        NavigationModule::class
    ]
)
interface AppComponent {
    fun inject(application: FinanceApplication)

    fun expensesComponentBuilder(): ExpensesComponent.Builder
    fun checkComponentBuilder(): AccountComponent.Builder
    fun articlesComponentBuilder(): CategoryComponent.Builder
    fun historyComponentBuilder(): HistoryComponent.Builder
    fun incomeComponentBuilder(): IncomeComponent.Builder
    fun settingsComponentBuilder(): SettingsComponent.Builder
    fun transactionComponentBuilder(): TransactionComponent.Builder

    fun historyNavigation(): HistoryNavigation

    fun application(): Application

    @ApplicationContext
    fun applicationContext(): Context

    @IoDispatchers
    fun ioDispatcher(): CoroutineDispatcher

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}