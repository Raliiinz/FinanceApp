package com.example.financeapp.transaction.di

import com.example.financeapp.base.di.scopes.TransactionScope
import com.example.financeapp.transaction.screen.TransactionFormViewModelFactory
import dagger.Subcomponent

/**
 * Dagger Subcomponent, отвечающий за внедрение зависимостей для экрана формы транзакции.
 * Использует область действия [TransactionScope] и предоставляет фабрику ViewModel'ей.
 */
@TransactionScope
@Subcomponent(
    modules = []
)
interface TransactionComponent {
    fun getTransactionFormViewModelFactory(): TransactionFormViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): TransactionComponent
    }
}