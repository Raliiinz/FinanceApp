package com.example.financeapp.articles.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.articles.screen.CategoryScreenViewModel
import com.example.financeapp.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [CategoryScreenViewModel] к общей фабрике ViewModel'ей.
 * Использует multibindings для регистрации ViewModel в системе Dagger.
 */
@Module
interface CategoryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryScreenViewModel::class)
    fun bindCategoryViewModel(viewModel: CategoryScreenViewModel): ViewModel
}