package com.example.financeapp.di

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.history.navigation.HistoryNavigationImpl
import com.example.financeapp.navigation.HistoryNavigation
import dagger.Binds
import dagger.Module

/**
 * Dagger-модуль для навигации, предоставляющий реализации навигационных интерфейсов.
 *
 * Связывает HistoryNavigation с его реализацией HistoryNavigationImpl в рамках AppScope.
 */
@Module
interface NavigationModule {
    @Binds
    @AppScope
    fun bindHistoryNavigation(impl: HistoryNavigationImpl): HistoryNavigation
}