package com.example.financeapp.articles.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.articles.screen.CategoryScreen
import com.example.financeapp.articles.screen.CategoryScreenViewModel
import com.example.financeapp.base.R
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TopBarConfig

/**
 * Навигационный граф для раздела статей.
 */
fun NavGraphBuilder.articlesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
) {
    navigation(
        startDestination = "articles/main",
        route = Screen.Articles.route
    ) {
        composable("articles/main") { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.my_articles,
                            trailingImageResId = null,
                            onTrailingClick = null,
                            leadingImageResId = null,
                            onLeadingClick = null
                        )
                        updateTopBarState(backStackEntry, topBarConfig)
                    }
                }

                backStackEntry.lifecycle.addObserver(observer)

                onDispose {
                    backStackEntry.lifecycle.removeObserver(observer)
                    updateTopBarState(backStackEntry, null)
                }
            }

        ArticlesRoute(
                paddingValues = paddingValues,
                onArticleClicked = { id -> /* handle tap */ },
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                )
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
    viewModel: CategoryScreenViewModel
) {

    CategoryScreen (
        viewModel = viewModel,
        paddingValues = paddingValues,
        onArticleClicked = onArticleClicked,
        onSearchClicked = {}
    )
}