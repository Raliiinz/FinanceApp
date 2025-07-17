package com.example.financeapp.analysis.navigation.state

import com.example.financeapp.navigation.TransactionType

sealed class AnalysisEvent {
    data class DateChanged(
        val transactionType: TransactionType,
        val from: String,
        val to: String
    ) : AnalysisEvent()
    object HideErrorDialog : AnalysisEvent()
}