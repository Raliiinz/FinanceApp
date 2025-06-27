package com.example.financeapp.navigation

/**
 * Интерфейс для навигации к экрану истории транзакций.
 */
interface HistoryNavigation {
    fun navigateToHistory(transactionType: TransactionType): String
}
