package com.example.financeapp.domain.di

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.domain.di.qualifies.IoDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Модуль для предоставления зависимостей уровня Domain
 */
@Module
class DomainModule {

    @Provides
    @AppScope
    @IoDispatchers
    fun provideIoDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }
}
