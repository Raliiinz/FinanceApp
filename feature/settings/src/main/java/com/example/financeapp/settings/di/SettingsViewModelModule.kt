package com.example.financeapp.settings.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.settings.screen.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger модуль для привязки [SettingsViewModel] к карте ViewModel'ей для ViewModelFactory.
 */
@Module
interface SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindExpensesViewModel(viewModel: SettingsViewModel): ViewModel
}