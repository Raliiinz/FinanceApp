package com.example.financeapp.history.di

import com.example.financeapp.history.navigation.HistoryNavigationImpl
import com.example.financeapp.navigation.HistoryNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
/**
 * DI модуль для раздела истории.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryModule {

    @Binds
    abstract fun bindHistoryNavigation(impl: HistoryNavigationImpl): HistoryNavigation
}