package com.example.financeapp.expenses.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [ExpensesScreenViewModel] к общей фабрике ViewModel'ей.
 * Используется при построении компоненты [ExpensesComponent].
 */
@Module
interface ExpensesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesScreenViewModel::class)
    fun bindExpensesViewModel(viewModel: ExpensesScreenViewModel): ViewModel
}