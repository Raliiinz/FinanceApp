package com.example.financeapp.expenses.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.expenses.screen.ExpensesScreen
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import com.example.financeapp.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = ExpensesScreens.Main.route,
        route = Screen.Expenses.route
    ) {
        composable(route = ExpensesScreens.Main.route) {
            ExpensesRoute(
                navController = navController,
                paddingValues = paddingValues,
                onExpenseClicked = { id -> /* Handle click */ },
                onFabClick = { /* FloatingAction */ }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensesRoute(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    ExpensesScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onExpenseClicked = onExpenseClicked,
        onFabClick = onFabClick,
    )
}

sealed class ExpensesScreens(val route: String) {
    object Main : ExpensesScreens("expenses/main")
}