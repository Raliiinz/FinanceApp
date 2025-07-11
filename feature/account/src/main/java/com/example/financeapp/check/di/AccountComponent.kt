package com.example.financeapp.check.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.AccountScope
import dagger.Subcomponent

/**
 * Dagger-компонент для работы с аккаунтами.
 * Предоставляет фабрику ViewModel'ей в рамках AccountScope.
 */
@AccountScope
@Subcomponent(
    modules = [
        AccountViewModelModule::class
    ]
)
interface AccountComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): AccountComponent
    }
}