package com.example.financeapp.di

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.history.navigation.HistoryNavigationImpl
import com.example.financeapp.navigation.HistoryNavigation
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {
    @Binds // Используем @Binds для связывания интерфейса с реализацией
    @AppScope // Убедимся, что HistoryNavigation является синглтоном на уровне приложения
    fun bindHistoryNavigation(impl: HistoryNavigationImpl): HistoryNavigation
}