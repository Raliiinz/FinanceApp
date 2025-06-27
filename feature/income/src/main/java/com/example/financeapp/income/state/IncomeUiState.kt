package com.example.financeapp.income.state

import androidx.annotation.StringRes
import com.example.financeapp.domain.model.TransactionModel

/**
 * Состояния UI экрана доходов.
 */
sealed class IncomeUiState {
    object Loading : IncomeUiState()
    data class Success(val incomes: List<TransactionModel>) : IncomeUiState()
    data class Error(@StringRes val messageRes: Int) : IncomeUiState()
    object Idle : IncomeUiState()
}