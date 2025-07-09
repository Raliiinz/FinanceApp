package com.example.financeapp.expenses.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap // Не забудьте добавить зависимость dagger-android-support

@Module
interface ExpensesViewModelModule {

    @Binds
    @IntoMap // Добавляет ViewModel в Map<Class<out ViewModel>, Provider<ViewModel>>
    @ViewModelKey(ExpensesScreenViewModel::class) // Связываем с ExpensesViewModel
    fun bindExpensesViewModel(viewModel: ExpensesScreenViewModel): ViewModel
}