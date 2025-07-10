package com.example.financeapp.check.navigation

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
import com.example.financeapp.check.main.screen.AccountScreen
import com.example.financeapp.check.main.screen.AccountScreenViewModel
import com.example.financeapp.check.update.screen.AccountUpdateScreen
import com.example.financeapp.check.update.screen.AccountUpdateViewModel
import com.example.financeapp.check.update.state.AccountUpdateEvent
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TopBarConfig

/**
 * Навигационный граф для экранов раздела "Чеки/Счета".
 * Содержит route для главного экрана счетов и обработку навигации.
 */
fun NavGraphBuilder.checkNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
) {
    navigation(
        startDestination = CheckRoutes.CHECK_MAIN,
        route = Screen.Check.route
    ) {
        composable("check/main") { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.my_check,
                            trailingImageResId = R.drawable.pencil,
                            onTrailingClick = {
                                navController.navigate(CheckRoutes.CHECK_EDIT_FROM_TOPBAR)
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

            ChecksRoute(
                paddingValues = paddingValues,
                onCheckClicked = { id -> /* handle tap */ },
                onFabClick = { /* handle FAB */ },
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                )
            )
        }

        composable(CheckRoutes.CHECK_EDIT_FROM_TOPBAR) { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current
            val accountUpdateViewModel: AccountUpdateViewModel = viewModel(
                factory = viewModelFactory,
                viewModelStoreOwner = backStackEntry
            )

            DisposableEffect(lifecycleOwner, backStackEntry) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        val topBarConfig = TopBarConfig(
                            textResId = R.string.my_check,
                            trailingImageResId = R.drawable.ic_done,
                            onTrailingClick = {
                                accountUpdateViewModel.reduce(AccountUpdateEvent.OnDoneClicked)
                            },
                            leadingImageResId = R.drawable.ic_close,
                            onLeadingClick = {
                                navController.popBackStack()
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

            EditAccountRoute(
                accountId = null,
                navController = navController,
                paddingValues = paddingValues,
                viewModel = accountUpdateViewModel
            )
        }

    }
}

/**
 * Основной route для экрана счетов.
 * Обертка для AccountScreen с обработкой событий.
 */
@Composable
fun ChecksRoute(
    paddingValues: PaddingValues,
    onCheckClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: AccountScreenViewModel
) {
    AccountScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onAccountClick = onCheckClicked,
        onFabClick = onFabClick,
    )
}

/**
 * Composable-обертка для экрана редактирования счета.
 * Принимает `accountId: Int?` (может быть null).
 */
@Composable
fun EditAccountRoute(
    accountId: Int?, // Если null, значит ID нужно получить во ViewModel
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: AccountUpdateViewModel
) {
    AccountUpdateScreen(
        paddingValues = paddingValues,
        onCloseClick = { navController.popBackStack() },
        onSaveClick = { navController.popBackStack() },
        viewModel = viewModel
    )
}

object CheckRoutes {
    const val CHECK_MAIN = "check/main"
    const val CHECK_EDIT_FROM_TOPBAR = "check/edit_from_topbar"
}