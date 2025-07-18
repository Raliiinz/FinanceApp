package com.example.financeapp.analysis.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.analysis.screen.AnalysisScreenViewModel
import com.example.financeapp.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [AnalysisScreenViewModel] к ViewModelFactory с использованием multibindings.
 */
@Module
interface AnalysisViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AnalysisScreenViewModel::class)
    fun bindExpensesViewModel(viewModel: AnalysisScreenViewModel): ViewModel
}