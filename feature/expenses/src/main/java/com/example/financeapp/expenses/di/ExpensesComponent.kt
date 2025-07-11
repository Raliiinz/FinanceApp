package com.example.financeapp.expenses.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.ExpensesScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [ExpensesScope], предоставляющий зависимости для экрана расходов.
 * Отвечает за инициализацию ViewModelFactory, содержащего [ExpensesScreenViewModel].
 */
@ExpensesScope
@Subcomponent(
    modules = [
        ExpensesViewModelModule::class
    ]
)
interface ExpensesComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): ExpensesComponent
    }
}