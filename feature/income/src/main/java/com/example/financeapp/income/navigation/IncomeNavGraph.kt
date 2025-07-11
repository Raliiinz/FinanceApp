package com.example.financeapp.income.navigation

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
import com.example.financeapp.base.R
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.income.screen.IncomeScreen
import com.example.financeapp.income.screen.IncomeScreenViewModel
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType

/**
 * Навигационный граф для раздела доходов.
 */
fun NavGraphBuilder.incomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
    historyNavigation: HistoryNavigation
) {
    navigation(
        startDestination = "income/main",
        route = Screen.Income.route
    ) {
        composable(route = "income/main") { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.income_today,
                            trailingImageResId = R.drawable.refresh,
                            onTrailingClick = {
                                navController.navigate(historyNavigation.navigateToHistory(TransactionType.INCOME))
                            },
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

            IncomeRoute(
                navController = navController,
                paddingValues = paddingValues,
                onIncomeClicked = { id -> /* Handle click */ },
                onFabClick = { /* FloatingAction */ },
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                )
            )
        }
    }
}

/**
 * Route для экрана доходов.
 * Обертка для IncomeScreen с обработкой навигации.
 */
@Composable
fun IncomeRoute(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: IncomeScreenViewModel
) {
    IncomeScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onIncomeClicked = { id ->
            navController.navigate("transaction/edit/${id}")
        },
        onFabClick = { navController.navigate(Screen.Transaction.createAddRoute(TransactionType.INCOME)) },
    )
}
