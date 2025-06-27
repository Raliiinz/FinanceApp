package com.example.financeapp.articles.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.articles.screen.CategoryScreen
import com.example.financeapp.articles.screen.CategoryScreenViewModel
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для раздела статей.
 */
fun NavGraphBuilder.articlesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "articles/main",
        route = Screen.Articles.route
    ) {
        composable("articles/main") {
            ArticlesRoute(
                paddingValues = paddingValues,
                onArticleClicked = { id -> /* handle tap */ },
            )
        }
    }
}

/**
 * Route для экрана статей.
 * Обертка для CategoryScreen с обработкой навигации.
 */
@Composable
fun ArticlesRoute(
    paddingValues: PaddingValues,
    onArticleClicked: (Int) -> Unit,
    viewModel: CategoryScreenViewModel = hiltViewModel()
) {

    CategoryScreen (
        viewModel = viewModel,
        paddingValues = paddingValues,
        onArticleClicked = onArticleClicked,
        onSearchClicked = {}
    )
}