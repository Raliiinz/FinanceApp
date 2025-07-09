package com.example.financeapp.check.di

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.di.ViewModelKey
import com.example.financeapp.check.main.screen.AccountScreenViewModel
import com.example.financeapp.check.update.screen.AccountUpdateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap // Не забудьте добавить зависимость dagger-android-support

@Module
interface AccountViewModelModule {

    @Binds @IntoMap @ViewModelKey(AccountScreenViewModel::class) fun bindAccountScreenViewModel(viewModel: AccountScreenViewModel): ViewModel
    @Binds @IntoMap @ViewModelKey(AccountUpdateViewModel::class) fun bindAccountUpdateScreenViewModel(viewModel: AccountUpdateViewModel): ViewModel
}