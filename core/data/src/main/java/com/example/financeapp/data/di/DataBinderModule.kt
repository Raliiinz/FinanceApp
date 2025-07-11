package com.example.financeapp.data.di

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.repository.AccountRepositoryImpl
import com.example.financeapp.data.repository.CategoryRepositoryImpl
import com.example.financeapp.data.repository.TransactionRepositoryImpl
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module

/**
 * Модуль для привязки реализаций репозиториев к их интерфейсам.
 */

@Module
interface DataBinderModule {
    @Binds
    @AppScope
    fun bindAccountRepositoryToImpl(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @AppScope
    fun bindCategoryRepositoryToImpl(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @AppScope
    fun bindTransactionRepositoryToImpl(impl: TransactionRepositoryImpl): TransactionRepository
}
