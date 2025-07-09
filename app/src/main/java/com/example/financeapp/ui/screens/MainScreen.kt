package com.example.financeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.check.navigation.CheckRoutes
import com.example.financeapp.extensions.shouldHideTopBar
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.RootNavGraph
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.ui.components.AppBottomBar
import com.example.financeapp.ui.components.AppTopBar

/**
 * Главный экран приложения с навигацией и scaffold-структурой.
 */
@Composable
fun MainScreen(
    historyNavigation: HistoryNavigation
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val transactionType = navBackStackEntry?.arguments?.getSerializable("type") as? TransactionType
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentRoute != CheckRoutes.CHECK_EDIT_FROM_TOPBAR) {
                AppTopBar(
                    currentDestination = currentDestination,
                    navController = navController,
                    historyNavigation = historyNavigation
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                navController = navController,
                currentDestination = currentDestination,
                activeTransactionType = transactionType
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            RootNavGraph(
                navController = navController,
                paddingValues = innerPadding,
            )
        }
    }
}
