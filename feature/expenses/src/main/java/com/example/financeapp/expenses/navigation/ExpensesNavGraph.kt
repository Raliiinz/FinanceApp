package com.example.financeapp.expenses.navigation

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
import com.example.financeapp.expenses.screen.ExpensesScreen
import com.example.financeapp.expenses.screen.ExpensesScreenViewModel
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType

/**
 * Навигационный граф для раздела расходов.
 */
fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
    historyNavigation: HistoryNavigation
) {
    navigation(
        startDestination = "expenses/main",
        route = Screen.Expenses.route
    ) {
        composable(route = "expenses/main") {  backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.expenses_today,
                            trailingImageResId = R.drawable.refresh,
                            onTrailingClick = {
                                navController.navigate(historyNavigation.navigateToHistory(TransactionType.EXPENSE))
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

            ExpensesRoute(
                navController = navController,
                paddingValues = paddingValues,
                onExpenseClicked = { id -> /* Handle click */ },
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
 * Route для экрана расходов.
 * Обертка для ExpensesScreen с обработкой навигации.
 */
@Composable
fun ExpensesRoute(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: ExpensesScreenViewModel
) {
    ExpensesScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onExpenseClicked = { id ->
            navController.navigate("transaction/edit/${id}")
        },
        onFabClick =  { navController.navigate(Screen.Transaction.createAddRoute(TransactionType.EXPENSE)) },
    )
}
