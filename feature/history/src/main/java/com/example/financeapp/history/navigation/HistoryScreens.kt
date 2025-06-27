package com.example.financeapp.history.navigation

import com.example.financeapp.navigation.TransactionType
/**
 * Экраны раздела истории.
 */
sealed class HistoryScreens(val route: String) {
    object Main : HistoryScreens("main?type={type}") {
        fun createRoute(type: TransactionType) = "main?type=${type.name}"
    }
}