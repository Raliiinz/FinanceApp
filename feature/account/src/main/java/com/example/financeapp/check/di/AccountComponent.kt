package com.example.financeapp.check.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.AccountScope
import com.example.financeapp.base.di.scopes.ExpensesScope
import dagger.Subcomponent

@AccountScope // Применяем наш кастомный Scope
@Subcomponent(
    modules = [
        // Для специфичных зависимостей фичи (если есть)
        AccountViewModelModule::class // Для ViewModel
    ]
)
interface AccountComponent {

    // Метод для инъекции в ExpensesHostActivity
//    fun inject(activity: ExpensesHostActivity)

    // Метод для получения ViewModelFactory, чтобы использовать его в Composable
    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): AccountComponent
    }
}