package com.example.financeapp.extensions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.financeapp.check.navigation.CheckRoutes
import com.example.financeapp.history.navigation.HistoryScreens
import com.example.financeapp.navigation.NavigationItem
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TransactionType

/**
 * Файл с extension-функциями для навигации.
 * Содержит вспомогательные методы для работы с Navigation Component.
 */

fun NavController.navigateToItem(item: NavigationItem) {
    navigate(item.screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
