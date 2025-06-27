package com.example.financeapp.expenses.state

import androidx.annotation.StringRes
import com.example.financeapp.domain.model.TransactionModel

/**
 * Состояния UI экрана расходов.
 */
sealed class ExpensesUiState {
    object Loading : ExpensesUiState()
    data class Success(val transactions: List<TransactionModel>) : ExpensesUiState()
    data class Error(@StringRes val messageRes: Int) : ExpensesUiState()
    object Idle : ExpensesUiState()
}