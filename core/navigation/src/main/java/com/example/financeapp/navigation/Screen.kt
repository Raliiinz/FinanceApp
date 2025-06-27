package com.example.financeapp.navigation

/**
 * Запечатанный класс, определяющий маршруты экранов в приложении.
 */
sealed class Screen(val route: String) {
    object Expenses : Screen("expenses_graph")
    object Income   : Screen("income_graph")
    object Check    : Screen("check_graph")
    object Articles : Screen("articles_graph")
    object Settings : Screen("settings_graph")
}