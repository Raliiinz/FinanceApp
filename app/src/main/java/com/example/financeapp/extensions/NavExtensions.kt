package com.example.financeapp.extensions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.financeapp.history.navigation.HistoryScreens
import com.example.financeapp.navigation.NavigationItem
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TransactionType

// Extension functions для проверки текущего экрана
fun NavDestination?.isExpensesScreen() =
    this?.hierarchy?.any { it.route == Screen.Expenses.route } == true

fun NavDestination?.isIncomeScreen() =
    this?.hierarchy?.any { it.route == Screen.Income.route } == true

fun NavDestination?.isSettingsScreen() =
    this?.hierarchy?.any { it.route == Screen.Settings.route } == true

fun NavDestination?.isArticlesScreen() =
    this?.hierarchy?.any { it.route == Screen.Articles.route } == true

fun NavDestination?.isCheckScreen() =
    this?.hierarchy?.any { it.route == Screen.Check.route } == true

// Файл: com/example/financeapp/extensions/NavExtensions.kt
fun NavDestination?.isHistoryScreen(): Boolean {
    val historyRoute = HistoryScreens.Main.route.substringBefore("?")
    return this?.route?.contains(historyRoute) == true
}
// Получение типа транзакции из аргументов навигации
fun NavBackStackEntry?.getTransactionType(): TransactionType? =
    this?.arguments?.getSerializable("type") as? TransactionType

// Проверка выбранного элемента в BottomNavBar
fun NavDestination?.isItemSelected(
    item: NavigationItem,
    transactionType: TransactionType? = null
): Boolean {
    return when {
        this?.hierarchy?.any { it.route == item.screen.route } == true -> true
        this?.route?.startsWith("main") == true -> when (item) {
            NavigationItem.Income -> transactionType == TransactionType.INCOME
            NavigationItem.Expenses -> transactionType == TransactionType.EXPENSE
            else -> false
        }
        else -> false
    }
}

// Навигация к элементу с правильными флагами
fun NavController.navigateToItem(item: NavigationItem) {
    navigate(item.screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}