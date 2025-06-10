package com.example.financeapp.expenses.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.navigation.Routes
import com.example.financeapp.navigation.Screen

fun NavGraphBuilder.expensesNavGraph(navController: NavHostController) {
    navigation(
        startDestination = "expenses/main",
        route = Screen.Expenses.route
    ) {
        composable("expenses/main") {
            ExpensesScreen()
        }
    }
}

@Composable
fun ExpensesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Expenses Screen")
    }
}