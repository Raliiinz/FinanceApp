package com.example.financeapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.financeapp.articles.navigation.articlesNavGraph
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.check.navigation.checkNavGraph
import com.example.financeapp.expenses.navigation.expensesNavGraph
import com.example.financeapp.history.navigation.historyNavGraph
import com.example.financeapp.income.navigation.incomeNavGraph
import com.example.financeapp.settings.navigation.settingsNavGraph
import com.example.financeapp.transaction.navigation.transactionNavGraph

/**
 * Хост навигации, содержащий все графы навигации приложения.
 */
@Composable
fun RootNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    expensesViewModelFactory: ViewModelFactory,
    historyViewModelFactory: ViewModelFactory,
    incomeViewModelFactory: ViewModelFactory,
    checkViewModelFactory: ViewModelFactory,
    articlesViewModelFactory: ViewModelFactory,
    settingsViewModelFactory: ViewModelFactory,
    transactionViewModelFactory: ViewModelFactory,
    historyNavigation: HistoryNavigation,
    updateTopBarState: (NavBackStackEntry, TopBarConfig?) -> Unit,

) {
    NavHost(
        navController = navController,
        startDestination = Screen.Expenses.route,
    ) {
        expensesNavGraph(navController, paddingValues, expensesViewModelFactory, updateTopBarState, historyNavigation)
        incomeNavGraph(navController, paddingValues, incomeViewModelFactory, updateTopBarState, historyNavigation)
        checkNavGraph(navController, paddingValues, checkViewModelFactory, updateTopBarState)
        articlesNavGraph(navController, paddingValues, articlesViewModelFactory, updateTopBarState)
        settingsNavGraph(navController, paddingValues, settingsViewModelFactory, updateTopBarState)
        historyNavGraph(navController, paddingValues, historyViewModelFactory, updateTopBarState)
        transactionNavGraph(navController, paddingValues, transactionViewModelFactory, updateTopBarState)
    }
}
