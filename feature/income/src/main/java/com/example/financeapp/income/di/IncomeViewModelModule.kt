package com.example.financeapp.income.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.income.screen.IncomeScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [IncomeScreenViewModel] к карте ViewModel'ей для ViewModelFactory.
 */
@Module
interface IncomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(IncomeScreenViewModel::class)
    fun bindExpensesViewModel(viewModel: IncomeScreenViewModel): ViewModel
}