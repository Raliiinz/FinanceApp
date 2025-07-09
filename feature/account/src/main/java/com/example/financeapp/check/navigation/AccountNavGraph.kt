package com.example.financeapp.check.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.check.main.screen.AccountScreen
import com.example.financeapp.check.main.screen.AccountScreenViewModel
import com.example.financeapp.check.update.screen.AccountUpdateScreen
import com.example.financeapp.navigation.Screen

/**
 * Навигационный граф для экранов раздела "Чеки/Счета".
 * Содержит route для главного экрана счетов и обработку навигации.
 */
fun NavGraphBuilder.checkNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = CheckRoutes.CHECK_MAIN,
        route = Screen.Check.route
    ) {
        composable("check/main") {
            ChecksRoute(
                paddingValues = paddingValues,
                onCheckClicked = { id -> /* handle tap */ },
                onFabClick = { /* handle FAB */ }
            )
        }

        composable(CheckRoutes.CHECK_EDIT_FROM_TOPBAR) {
            EditAccountRoute(
                accountId = null,
                navController = navController,
                paddingValues = paddingValues
            )
        }
        // 1.4. (ОПЦИОНАЛЬНО) Если вы когда-нибудь захотите редактировать по ID, раскомментируйте это:
        /*
        composable(
            route = CheckRoutes.CHECK_EDIT_SPECIFIC_ACCOUNT,
            arguments = listOf(navArgument("accountId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getInt("accountId")
            EditAccountRoute(
                accountId = accountId, // Передаем полученный ID
                navController = navController,
                paddingValues = paddingValues
            )
        }
        */
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
    viewModel: AccountScreenViewModel = hiltViewModel()
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
    // viewModel: EditAccountViewModel = hiltViewModel() // Ваша ViewModel для этого экрана
) {
    AccountUpdateScreen(
        paddingValues = paddingValues,
        onCloseClick = { navController.popBackStack() },
        onSaveClick = { navController.popBackStack() },
    )
}

object CheckRoutes {
    const val CHECK_MAIN = "check/main"
    const val CHECK_EDIT_FROM_TOPBAR = "check/edit_from_topbar"

}