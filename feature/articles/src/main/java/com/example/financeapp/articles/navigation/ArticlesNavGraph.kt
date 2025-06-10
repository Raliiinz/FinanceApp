package com.example.financeapp.articles.navigation

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

fun NavGraphBuilder.articlesNavGraph(navController: NavHostController) {
    navigation(
        startDestination = "articles/main",
        route = Screen.Articles.route
    ) {
        composable("articles/main") {
            ArticlesScreen()
        }
    }
}

@Composable
fun ArticlesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Articles Screen")
    }
}