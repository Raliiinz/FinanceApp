package com.example.financeapp.transaction.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.transaction.screen.TransactionFormViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Модуль Dagger, ответственный за привязку [TransactionFormViewModel] в карту ViewModel'ей.
 */
@Module
interface TransactionViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TransactionFormViewModel::class)
    fun bindTransactionFormViewModel(viewModel: TransactionFormViewModel): ViewModel
}
