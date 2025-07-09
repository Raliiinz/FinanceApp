package com.example.financeapp.settings.di

import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.di.scopes.CategoryScope
import com.example.financeapp.base.di.scopes.SettingsScope
import dagger.Subcomponent

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