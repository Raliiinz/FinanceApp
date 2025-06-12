package com.example.financeapp.income.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.income.screen.IncomeScreen
import com.example.financeapp.income.screen.IncomeScreenViewModel
import com.example.financeapp.navigation.Screen

fun NavGraphBuilder.incomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "income/main",
        route =  Screen.Income.route
    ) {
        composable("income/main") {
            IncomeRoute(
                paddingValues = paddingValues,
                onIncomeClicked = { id -> /* TODO */ },
                onFabClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun IncomeRoute(
    viewModel: IncomeScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val incomeList by viewModel.incomeList.collectAsState()

    IncomeScreen(
        incomeList = incomeList,
        paddingValues = paddingValues,
        onIncomeClicked = onIncomeClicked,
        onFabClick = onFabClick
    )
}