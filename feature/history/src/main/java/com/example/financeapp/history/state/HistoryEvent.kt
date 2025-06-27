package com.example.financeapp.history.state

import com.example.financeapp.navigation.TransactionType
/**
 * События экрана истории транзакций.
 */
sealed class HistoryEvent {
    data class DateChanged(
        val transactionType: TransactionType,
        val from: String,
        val to: String
    ) : HistoryEvent()

    object HideErrorDialog : HistoryEvent()
}