package com.example.financeapp.transaction.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.transaction.screen.TransactionFormScreen
import com.example.financeapp.transaction.screen.TransactionFormViewModel
import com.example.financeapp.transaction.state.TransactionFormEvent

/**
 * Навигационный граф для создания и редактирования транзакций.
 *
 * @param navController Контроллер навигации.
 * @param paddingValues Отступы верхнего уровня (например, от системы).
 * @param viewModelFactory Фабрика для создания ViewModel'ей.
 * @param updateTopBarState Функция для конфигурации верхней панели (TopBar).
 */
@SuppressLint("StateFlowValueCalledInComposition")
fun NavGraphBuilder.transactionNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
) {
    navigation(
        startDestination = Screen.Transaction.createAddRoute(TransactionType.EXPENSE),
        route = Screen.Transaction.route
    ) {
        composable(
            route = Screen.Transaction.addRouteTemplate,
            arguments = listOf(navArgument("type") {
                type = NavType.EnumType(TransactionType::class.java)
            })
        ) { backStackEntry ->
            val typeArg = backStackEntry.arguments?.get("type")

            val lifecycleOwner = LocalLifecycleOwner.current
            val transactionType = backStackEntry.arguments?.getSerializable("type") as TransactionType

            val transactionFormViewModel: TransactionFormViewModel = viewModel(
                factory = viewModelFactory,
                viewModelStoreOwner = backStackEntry
            )

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val titleResId = if (transactionType == TransactionType.EXPENSE) R.string.my_expenses else R.string.my_income
                        val topBarConfig = TopBarConfig(
                            textResId = titleResId,
                            leadingImageResId = R.drawable.ic_close,
                            onLeadingClick = { navController.popBackStack() },
                            trailingImageResId = R.drawable.ic_done,
                            onTrailingClick = {
                                transactionFormViewModel.handleEvent(TransactionFormEvent.SaveTransaction)
                            }
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

            TransactionFormScreen(
                paddingValues = paddingValues,
                viewModel = transactionFormViewModel,
                onTransactionSaved = { navController.popBackStack() },
                onClose = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Transaction.editRouteTemplate,
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val idArg = backStackEntry.arguments?.get("transactionId")

            val lifecycleOwner = LocalLifecycleOwner.current
            val transactionId = backStackEntry.arguments?.getInt("transactionId")

            val transactionFormViewModel: TransactionFormViewModel = viewModel(
                factory = viewModelFactory,
                viewModelStoreOwner = backStackEntry
            )

            DisposableEffect(lifecycleOwner, backStackEntry, transactionFormViewModel.uiState.value.isEditing, transactionFormViewModel.uiState.value.type) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val isEditingFromViewModel = transactionFormViewModel.uiState.value.isEditing
                        val isIncomeFromViewModel = transactionFormViewModel.uiState.value.type == TransactionType.INCOME

                        val titleResId = if (isEditingFromViewModel) {
                            if (isIncomeFromViewModel) R.string.my_income else R.string.my_expenses
                        } else {
                            if (isIncomeFromViewModel) R.string.my_income else R.string.my_expenses
                        }

                        val topBarConfig = TopBarConfig(
                            textResId = titleResId,
                            leadingImageResId = R.drawable.ic_close,
                            onLeadingClick = { navController.popBackStack() },
                            trailingImageResId = R.drawable.ic_done,
                            onTrailingClick = {
                                transactionFormViewModel.handleEvent(TransactionFormEvent.SaveTransaction)
                            }
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

            TransactionFormScreen(
                paddingValues = paddingValues,
                viewModel = transactionFormViewModel,
                onTransactionSaved = { navController.popBackStack() },
                onClose = { navController.popBackStack() }
            )
        }
    }
}