package com.example.financeapp.di

import dagger.Module

@Module
interface AppModule {
    // Здесь могут быть @Binds или @Provides для общих зависимостей уровня приложения,
    // которые не относятся к core:network, core:data, core:domain.
    // Например, если у вас есть общий менеджер настроек или навигации.
}