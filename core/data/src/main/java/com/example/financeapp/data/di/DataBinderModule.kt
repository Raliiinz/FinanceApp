package com.example.financeapp.data.di

import com.example.financeapp.data.repository.AccountRepositoryImpl
import com.example.financeapp.data.repository.CategoryRepositoryImpl
import com.example.financeapp.data.repository.TransactionRepositoryImpl
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataBinderModule {
    @Binds
    @Singleton
    fun bindAccountRepositoryToImpl(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindCategoryRepositoryToImpl(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindTransactionRepositoryToImpl(impl: TransactionRepositoryImpl): TransactionRepository
}
