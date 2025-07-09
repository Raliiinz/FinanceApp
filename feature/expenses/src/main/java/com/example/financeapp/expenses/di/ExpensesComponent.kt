package com.example.financeapp.expenses.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.ExpensesScope
import dagger.Subcomponent

@ExpensesScope // Применяем наш кастомный Scope
@Subcomponent(
    modules = [
        // Для специфичных зависимостей фичи (если есть)
        ExpensesViewModelModule::class // Для ViewModel
    ]
)
interface ExpensesComponent {

    // Метод для инъекции в ExpensesHostActivity
//    fun inject(activity: ExpensesHostActivity)

    // Метод для получения ViewModelFactory, чтобы использовать его в Composable
    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): ExpensesComponent
    }
}