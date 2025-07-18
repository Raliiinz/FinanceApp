package com.example.financeapp.analysis.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.financeapp.base.R
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.financeapp.analysis.screen.AnalysisScreen
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType

fun NavGraphBuilder.analysisNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,
) {
    composable(
        route = AnalysisScreens.MAIN_ROUTE,
        arguments = listOf(navArgument(AnalysisScreens.TYPE_ARG) { type = NavType.StringType })
    ) { backStackEntry ->
        val typeString = backStackEntry.arguments?.getString(AnalysisScreens.TYPE_ARG)

        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner, backStackEntry) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    val topBarConfig = TopBarConfig(
                        textResId = R.string.analysis,
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

        val transactionType = TransactionType.valueOf(typeString ?: "INCOME")
        AnalysisScreen(
            viewModel = viewModel(
                factory = viewModelFactory,
                viewModelStoreOwner = backStackEntry
            ),
            paddingValues = paddingValues,
            transactionType = transactionType
        )
    }
}