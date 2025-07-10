package com.example.financeapp.expenses.navigation

import android.util.Log
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
import com.example.financeapp.base.ui.commonItems.TopBarTextIcon
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
//    setTopBarContent: (NavBackStackEntry, @Composable (() -> Unit)?) -> Unit,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
//    setTopBarContent: (@Composable (() -> Unit)?) -> Unit,
//    setBottomBarContent: (@Composable (() -> Unit)?) -> Unit,
    historyNavigation: HistoryNavigation // HistoryNavigation здесь будет нужна для onTrailingClick
) {
    navigation(
        startDestination = "expenses/main",
        route = Screen.Expenses.route
    ) {
        composable(route = "expenses/main") {  backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            // Определяем контент TopBar для этого экрана
            DisposableEffect(lifecycleOwner, backStackEntry) { // <--- КЛЮЧЕВОЕ ИЗМЕНЕНИЕ
                val observer = LifecycleEventObserver { _, event ->
                    Log.d("NavGraph", "Screen: ${backStackEntry.destination.route}, Event: $event")
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
                        // Передаем TopBarState в MainScreen через updateTopBarState
                        updateTopBarState(backStackEntry, topBarConfig)
                        // Экран активен, устанавливаем TopBar
//                        setTopBarContent(backStackEntry) {
//                            TopBarTextIcon(
//                                textResId = R.string.expenses_today,
//                                trailingImageResId = R.drawable.refresh,
//                                onTrailingClicked = {
//                                    navController.navigate(
//                                        historyNavigation.navigateToHistory(
//                                            TransactionType.EXPENSE
//                                        )
//                                    )
//                                },
//                                leadingImageResId = null,
//                                onLeadingClicked = null
//                            )
//                        }
                    }
//                    } else if (event == Lifecycle.Event.ON_STOP) {
//                        Log.d("NavGraph", "Screen: ${backStackEntry.destination.route}, Clearing TopBarContent")
//                        // Экран больше не активен (ушел на фон или навигация вперед), очищаем TopBar
//                        setTopBarContent(null)
//                    }
                }

                backStackEntry.lifecycle.addObserver(observer)

                onDispose {
                    // Удаляем observer, когда NavBackStackEntry окончательно удаляется из стека
                    backStackEntry.lifecycle.removeObserver(observer)
                    Log.d("NavGraph", "Screen: ${backStackEntry.destination.route}, OnDispose, Clearing TopBarContent (final)")
                    updateTopBarState(backStackEntry, null) // Очищаем TopBar на случай, если onStop не был вызван
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
        onExpenseClicked = onExpenseClicked,
        onFabClick = onFabClick,
    )
}
