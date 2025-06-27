package com.example.financeapp.income.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.income.screen.IncomeScreen
import com.example.financeapp.income.screen.IncomeScreenViewModel
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для раздела доходов.
 */
fun NavGraphBuilder.incomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation(
        startDestination = "income/main",
        route = Screen.Income.route
    ) {
        composable(route = "income/main") {
            IncomeRoute(
                navController = navController,
                paddingValues = paddingValues,
                onIncomeClicked = { id -> /* Handle click */ },
                onFabClick = { /* FloatingAction */ },
            )
        }
    }
}

/**
 * Route для экрана доходов.
 * Обертка для IncomeScreen с обработкой навигации.
 */
@Composable
fun IncomeRoute(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: IncomeScreenViewModel = hiltViewModel()
) {
    IncomeScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onIncomeClicked = onIncomeClicked,
        onFabClick = onFabClick,
    )
}
