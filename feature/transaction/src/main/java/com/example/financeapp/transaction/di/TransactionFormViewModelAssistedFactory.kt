package com.example.financeapp.transaction.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.financeapp.transaction.screen.TransactionFormViewModelFactory

class TransactionFormViewModelAssistedFactory(
    private val assistedFactory: TransactionFormViewModelFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()
        return assistedFactory.create(savedStateHandle) as T
    }
}