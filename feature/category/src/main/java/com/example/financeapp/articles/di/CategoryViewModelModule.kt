package com.example.financeapp.articles.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.articles.screen.CategoryScreenViewModel
import com.example.financeapp.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryScreenViewModel::class)
    fun bindCategoryViewModel(viewModel: CategoryScreenViewModel): ViewModel
}