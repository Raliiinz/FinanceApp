package com.example.financeapp.check.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.check.screen.AccountScreen
import com.example.financeapp.check.screen.AccountScreenViewModel
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для экранов раздела "Чеки/Счета".
 * Содержит route для главного экрана счетов и обработку навигации.
 */
fun NavGraphBuilder.checkNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "check/main",
        route = Screen.Check.route
    ) {
        composable("check/main") {
            ChecksRoute(
                paddingValues = paddingValues,
                onCheckClicked = { id -> /* handle tap */ },
                onFabClick = { /* handle FAB */ }
            )
        }
    }
}

/**
 * Основной route для экрана счетов.
 * Обертка для AccountScreen с обработкой событий.
 */
@Composable
fun ChecksRoute(
    paddingValues: PaddingValues,
    onCheckClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: AccountScreenViewModel = hiltViewModel()
) {
    AccountScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onAccountClick = onCheckClicked,
        onFabClick = onFabClick,
    )
}