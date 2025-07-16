package com.example.financeapp.extensions

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.financeapp.navigation.NavigationItem

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
