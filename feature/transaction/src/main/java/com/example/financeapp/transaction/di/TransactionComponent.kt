package com.example.financeapp.transaction.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.TransactionScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent, отвечающий за внедрение зависимостей для экрана формы транзакции.
 * Использует область действия [TransactionScope] и предоставляет фабрику ViewModel'ей.
 */
@TransactionScope
@Subcomponent(
    modules = [
        TransactionModule::class,
        TransactionViewModelModule::class
    ]
)
interface TransactionComponent {
    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Builder interface Builder { fun build(): TransactionComponent }
}