package com.example.financeapp.data.di

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.repository.AccountRepositoryImpl
import com.example.financeapp.data.repository.CategoryRepositoryImpl
import com.example.financeapp.data.repository.TransactionRepositoryImpl
import com.example.financeapp.data.repository.local.AccountLocalRepositoryImpl
import com.example.financeapp.data.repository.local.CategoryLocalRepositoryImpl
import com.example.financeapp.data.repository.local.TransactionLocalRepositoryImpl
import com.example.financeapp.data.repository.remote.AccountRemoteRepositoryImpl
import com.example.financeapp.data.repository.remote.CategoryRemoteRepositoryImpl
import com.example.financeapp.data.repository.remote.TransactionRemoteRepositoryImpl
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.domain.repository.local.AccountLocalRepository
import com.example.financeapp.domain.repository.local.CategoryLocalRepository
import com.example.financeapp.domain.repository.local.TransactionLocalRepository
import com.example.financeapp.domain.repository.remote.AccountRemoteRepository
import com.example.financeapp.domain.repository.remote.CategoryRemoteRepository
import com.example.financeapp.domain.repository.remote.TransactionRemoteRepository
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

    @Binds
    @AppScope
    fun bindRemoteTransactionRepositoryToImpl(impl: TransactionRemoteRepositoryImpl): TransactionRemoteRepository

    @Binds
    @AppScope
    fun bindLocalTransactionRepositoryToImpl(impl: TransactionLocalRepositoryImpl): TransactionLocalRepository


    @Binds
    @AppScope
    fun bindRemoteAccountRepositoryToImpl(impl: AccountRemoteRepositoryImpl): AccountRemoteRepository

    @Binds
    @AppScope
    fun bindLocalAccountRepositoryToImpl(impl: AccountLocalRepositoryImpl): AccountLocalRepository

    @Binds
    @AppScope
    fun bindRemoteCategoryRepositoryToImpl(impl: CategoryRemoteRepositoryImpl): CategoryRemoteRepository

    @Binds
    @AppScope
    fun bindLocalCategoryRepositoryToImpl(impl: CategoryLocalRepositoryImpl): CategoryLocalRepository

}
