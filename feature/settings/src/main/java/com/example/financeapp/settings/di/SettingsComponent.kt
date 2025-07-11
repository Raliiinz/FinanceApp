package com.example.financeapp.settings.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.SettingsScope
import dagger.Subcomponent

/**
 * Dagger Subcomponent с областью действия [SettingsScope], предоставляющий зависимости для экрана настроек.
 * Предоставляет ViewModelFactory с зарегистрированным [SettingsViewModel].
 */
@SettingsScope
@Subcomponent(
    modules = [
        SettingsViewModelModule::class
    ]
)
interface SettingsComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): SettingsComponent
    }
}