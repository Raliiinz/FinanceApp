package com.example.financeapp.transaction.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
/**
 * Dagger-модуль, предоставляющий зависимости для работы с состоянием ViewModel'и.
 */
@Module
object TransactionModule {
    @Provides
    fun provideSavedStateHandle(): SavedStateHandle = SavedStateHandle()
}