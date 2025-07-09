package com.example.financeapp.income.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.income.screen.IncomeScreen
import com.example.financeapp.income.screen.IncomeScreenViewModel
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для раздела доходов.
 */
fun NavGraphBuilder.incomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    navigation(
        startDestination = "income/main",
        route = Screen.Income.route
    ) {
        composable(route = "income/main") { backStackEntry ->
            IncomeRoute(
                navController = navController,
                paddingValues = paddingValues,
                onIncomeClicked = { id -> /* Handle click */ },
                onFabClick = { /* FloatingAction */ },
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                )
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
    viewModel: IncomeScreenViewModel
) {
    IncomeScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onIncomeClicked = onIncomeClicked,
        onFabClick = onFabClick,
    )
}
