package com.example.financeapp.analysis.navigation.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.AnalysisScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [AnalysisScope], предоставляющий зависимости для экрана анализа.
 * Предоставляет ViewModelFactory с зарегистрированным [AnalysisScreenViewModel].
 */
@AnalysisScope
@Subcomponent(
    modules = [
        AnalysisViewModelModule::class
    ]
)
interface AnalysisComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): AnalysisComponent
    }
}