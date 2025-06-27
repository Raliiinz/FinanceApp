package com.example.financeapp.history.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.financeapp.history.screen.HistoryScreen
import com.example.financeapp.navigation.TransactionType

/**
 * Навигационный граф для раздела истории.
 */
fun NavGraphBuilder.historyNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = HistoryScreens.Main.route,
        route = "history_graph"
    ) {
        composable(
            route = HistoryScreens.Main.route,
            arguments = listOf(navArgument("type") {
                type = NavType.EnumType(TransactionType::class.java)
            })
        ) { backStackEntry ->
            val transactionType = backStackEntry.arguments?.getSerializable("type") as TransactionType
            HistoryScreen(
                transactionType = transactionType,
                onBackClick = { navController.popBackStack() },
                paddingValues = paddingValues
            )
        }
    }
}
