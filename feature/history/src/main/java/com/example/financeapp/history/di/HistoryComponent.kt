package com.example.financeapp.history.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.HistoryScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [HistoryScope], предоставляющий зависимости для экрана истории.
 * Предоставляет ViewModelFactory с зарегистрированным [HistoryScreenViewModel].
 */
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