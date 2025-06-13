package com.example.financeapp.data.di

import com.example.financeapp.data.repository.FinanceRepositoryImpl
import com.example.financeapp.domain.repository.FinanceRepository
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
    fun bindFinanceRepositoryToImpl(impl: FinanceRepositoryImpl): FinanceRepository
}
