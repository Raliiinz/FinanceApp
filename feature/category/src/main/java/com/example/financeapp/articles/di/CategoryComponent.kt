package com.example.financeapp.articles.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.CategoryScope
import com.example.financeapp.base.di.scopes.ExpensesScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [CategoryScope], предоставляющий зависимости для экрана категорий.
 * Отвечает за инициализацию ViewModelFactory, содержащего [CategoryScreenViewModel].
 */
@CategoryScope
@Subcomponent(
    modules = [
        CategoryViewModelModule::class
    ]
)
interface CategoryComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): CategoryComponent
    }
}