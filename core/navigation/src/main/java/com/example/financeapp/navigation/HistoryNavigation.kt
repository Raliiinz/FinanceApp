package com.example.financeapp.navigation

interface HistoryNavigation {
    fun navigateToHistory(transactionType: TransactionType): String
}