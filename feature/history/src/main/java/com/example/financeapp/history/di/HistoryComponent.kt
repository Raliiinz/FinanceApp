package com.example.financeapp.history.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.HistoryScope
import com.example.financeapp.base.di.scopes.IncomeScope
import dagger.Subcomponent

@HistoryScope
@Subcomponent(
    modules = [
        HistoryViewModelModule::class
    ]
)
interface HistoryComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): HistoryComponent
    }
}