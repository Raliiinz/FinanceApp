package com.example.financeapp.history.state

import androidx.annotation.StringRes
import com.example.financeapp.domain.model.TransactionModel
/**
 * Состояния UI экрана истории транзакций.
 */
sealed class HistoryUiState {
    object Loading : HistoryUiState()
    data class Success(val transactions: List<TransactionModel>, val currency: String) : HistoryUiState()
    data class Error(@StringRes val messageRes: Int) : HistoryUiState()
    object Idle : HistoryUiState()
}