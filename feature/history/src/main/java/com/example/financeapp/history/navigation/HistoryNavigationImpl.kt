package com.example.financeapp.history.navigation

import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.TransactionType
import javax.inject.Inject
/**
 * Реализация навигации для раздела истории.
 */
class HistoryNavigationImpl @Inject constructor() : HistoryNavigation {
    override fun navigateToHistory(transactionType: TransactionType): String {
        return HistoryScreens.Main.createRoute(transactionType)
    }
}

