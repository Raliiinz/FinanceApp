package com.example.financeapp.income.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.IncomeScope
import com.example.financeapp.base.di.scopes.SettingsScope
import dagger.Subcomponent

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