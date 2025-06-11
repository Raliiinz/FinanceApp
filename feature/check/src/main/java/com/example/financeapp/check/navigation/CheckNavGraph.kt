package com.example.financeapp.check.navigation

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

fun NavGraphBuilder.checkNavGraph(navController: NavHostController) {
    navigation(
        startDestination = "check/main",
        route = Screen.Check.route
    ) {
        composable("check/main") {
            CheckScreen()
        }
    }
}

@Composable
fun CheckScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Check Screen")
    }
}