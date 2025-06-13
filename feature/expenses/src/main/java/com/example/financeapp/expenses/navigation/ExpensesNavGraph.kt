package com.example.financeapp.expenses.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.expenses.screen.ExpensesScreen
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import com.example.financeapp.navigation.Screen

fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "expenses/main",
        route = Screen.Expenses.route
    ) {
        composable("expenses/main") {
            ExpensesRoute(
                paddingValues = paddingValues,
                onExpenseClicked = { id -> /* Handle click */ },
                onFabClick = { /* FloatingAction */ }
            )
        }
    }
}

@Composable
fun ExpensesRoute(
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ExpensesScreen(
        uiState = uiState,
        paddingValues = paddingValues,
        onExpenseClicked = onExpenseClicked,
        onFabClick = onFabClick
    )
}