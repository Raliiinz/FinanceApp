package com.example.financeapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.base.di.ViewModelFactory
import androidx.navigation.NavBackStackEntry
import com.example.financeapp.base.R
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.RootNavGraph
import com.example.financeapp.navigation.TopBarConfig
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.ui.components.AppBottomBar
import com.example.financeapp.ui.components.AppTopBar
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Главный экран приложения с навигацией и scaffold-структурой.
 */
@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(
    historyNavigation: HistoryNavigation,
    expensesViewModelFactory: ViewModelFactory,
    historyViewModelFactory: ViewModelFactory,
    incomeViewModelFactory: ViewModelFactory,
    checkViewModelFactory: ViewModelFactory,
    articlesViewModelFactory: ViewModelFactory,
    settingsViewModelFactory: ViewModelFactory,
    analysisViewModelFactory: ViewModelFactory
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val transactionType = navBackStackEntry?.arguments?.getSerializable("type") as? TransactionType

    val topBarStateMap = remember { mutableStateMapOf<NavBackStackEntry, TopBarConfig>() }
    val currentNavController by rememberUpdatedState(navController)
    val currentHistoryNavigation by rememberUpdatedState(historyNavigation)

    val currentTopBarStateFlow = remember {
        val initialTopBarConfig = TopBarConfig(
            textResId = R.string.expenses_today,
            trailingImageResId = R.drawable.refresh,
            onTrailingClick = {
                currentNavController.navigate(
                    currentHistoryNavigation.navigateToHistory(
                        TransactionType.EXPENSE
                    )
                )
            },
            leadingImageResId = null,
            onLeadingClick = null
        )
        MutableStateFlow<TopBarConfig?>(initialTopBarConfig)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(currentTopBarStateFlow) },
        bottomBar = {
            AppBottomBar(
                navController = navController,
                currentDestination = currentDestination,
                activeTransactionType = transactionType
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            RootNavGraph(
                navController = navController,
                paddingValues = innerPadding,
                expensesViewModelFactory = expensesViewModelFactory,
                historyViewModelFactory = historyViewModelFactory,
                incomeViewModelFactory = incomeViewModelFactory,
                articlesViewModelFactory = articlesViewModelFactory,
                settingsViewModelFactory = settingsViewModelFactory,
                checkViewModelFactory = checkViewModelFactory,
                analysisViewModelFactory = analysisViewModelFactory,
                historyNavigation = historyNavigation,

                updateTopBarState = { entry, newState ->
                    if (newState != null) {
                        topBarStateMap[entry] = newState
                    } else {
                        topBarStateMap.remove(entry)
                    }
                    currentTopBarStateFlow.value = navBackStackEntry?.let { activeEntry ->
                        topBarStateMap[activeEntry]
                    } ?: currentTopBarStateFlow.value
                }
            )
        }
    }
}