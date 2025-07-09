package com.example.financeapp.articles.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.CategoryScope
import com.example.financeapp.base.di.scopes.ExpensesScope
import dagger.Subcomponent

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