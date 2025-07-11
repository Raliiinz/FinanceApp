package com.example.financeapp.history.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.history.screen.HistoryScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [HistoryScreenViewModel] к ViewModelFactory с использованием multibindings.
 */
@Module
interface HistoryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryScreenViewModel::class)
    fun bindExpensesViewModel(viewModel: HistoryScreenViewModel): ViewModel
}