package com.example.financeapp.income.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.IncomeScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [IncomeScope], предоставляющий зависимости для экрана доходов.
 * Предоставляет ViewModelFactory с зарегистрированным [IncomeScreenViewModel].
 */
@IncomeScope
@Subcomponent(
    modules = [
        IncomeViewModelModule::class
    ]
)
interface IncomeComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): IncomeComponent
    }
}