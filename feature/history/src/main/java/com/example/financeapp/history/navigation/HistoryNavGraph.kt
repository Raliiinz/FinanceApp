package com.example.financeapp.history.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.financeapp.base.R
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.history.screen.HistoryScreen
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType

/**
 * Навигационный граф для раздела истории.
 */
fun NavGraphBuilder.historyNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
) {
    navigation(
        startDestination = HistoryScreens.Main.route,
        route = "history_graph"
    ) {
        composable(
            route = HistoryScreens.Main.route,
            arguments = listOf(navArgument("type") {
                type = NavType.EnumType(TransactionType::class.java)
            })
        ) { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            val transactionType = backStackEntry.arguments?.getSerializable("type") as TransactionType

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.history,
                            trailingImageResId = R.drawable.ic_analysis,
                            onTrailingClick = {
                                navController.navigate("analysis/main/${transactionType.name}")
                            },
                            leadingImageResId = R.drawable.ic_back,
                            onLeadingClick = { navController.popBackStack() }
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

            HistoryScreen(
                transactionType = transactionType,
                onBackClick = { navController.popBackStack() },
                paddingValues = paddingValues,
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                ),
                onTransactionClick = { id ->
                    navController.navigate("transaction/edit/$id")
                }
            )
        }
    }
}
