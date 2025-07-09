package com.example.financeapp.expenses.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.expenses.screen.ExpensesScreen
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для раздела расходов.
 */
fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    navigation(
        startDestination = "expenses/main",
        route = Screen.Expenses.route
    ) {
        composable(route = "expenses/main") {  backStackEntry ->
            ExpensesRoute(
                navController = navController,
                paddingValues = paddingValues,
                onExpenseClicked = { id -> /* Handle click */ },
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
 * Route для экрана расходов.
 * Обертка для ExpensesScreen с обработкой навигации.
 */
@Composable
fun ExpensesRoute(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: ExpensesScreenViewModel
) {
    ExpensesScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onExpenseClicked = onExpenseClicked,
        onFabClick = onFabClick,
    )
}
