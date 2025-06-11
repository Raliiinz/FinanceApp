package com.example.financeapp.income.navigation

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
import com.example.financeapp.navigation.Screen

fun NavGraphBuilder.incomeNavGraph(navController: NavHostController) {
    navigation(
        startDestination = "income/main",
        route =  Screen.Income.route
    ) {
        composable("income/main") {
            IncomeScreen()
        }
    }
}

@Composable
fun IncomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Income Screen")
    }
}