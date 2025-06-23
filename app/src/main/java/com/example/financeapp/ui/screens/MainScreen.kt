package com.example.financeapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.base.R
import androidx.compose.runtime.getValue
import com.example.financeapp.base.commonItems.TopBarTextIcon
import com.example.financeapp.domain.model.ScaffoldItemModel
import com.example.financeapp.history.navigation.HistoryScreens
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.RootNavGraph
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.ui.components.BottomNavBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    historyNavigation: HistoryNavigation
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    val transactionType = navBackStackEntry?.arguments?.getSerializable("type") as? TransactionType

    val topBarContent = when {
        destination?.hierarchy?.any { it.route == Screen.Expenses.route } == true -> {
            ScaffoldItemModel(textResId = R.string.expenses_today, trailingImageResId = R.drawable.refresh, onTrailingClicked = {
                val route = historyNavigation.navigateToHistory(TransactionType.EXPENSE)
                navController.navigate(route)
            })
        }
        destination?.hierarchy?.any { it.route == Screen.Settings.route } == true -> {
            ScaffoldItemModel(textResId = R.string.settings)
        }
        destination?.hierarchy?.any { it.route == Screen.Articles.route } == true -> {
            ScaffoldItemModel(textResId = R.string.my_articles)
        }
        destination?.hierarchy?.any { it.route == Screen.Income.route } == true -> {
            ScaffoldItemModel(textResId = R.string.income_today, trailingImageResId = R.drawable.refresh) {
                val route = historyNavigation.navigateToHistory(TransactionType.INCOME)
                navController.navigate(route)
            }
        }
        destination?.hierarchy?.any { it.route == Screen.Check.route } == true -> {
            ScaffoldItemModel(textResId = R.string.my_check, trailingImageResId = R.drawable.pencil)
        }

        destination?.route?.contains(HistoryScreens.Main.route.substringBefore("?")) == true -> {
            ScaffoldItemModel(
                textResId = R.string.history,
                leadingImageResId = R.drawable.ic_back,
                trailingImageResId = R.drawable.ic_analysis,
                onLeadingClicked = {
                    navController.popBackStack()
                },
            )
        }
        else -> null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            topBarContent?.let {
                TopBarTextIcon(
                    textResId = it.textResId,
                    leadingImageResId = it.leadingImageResId,
                    trailingImageResId = it.trailingImageResId,
                    onLeadingClicked = it.onLeadingClicked,
                    onTrailingClicked = it.onTrailingClicked
                )
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController, activeTransactionType = transactionType)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            RootNavGraph(
                navController = navController,
                paddingValues = innerPadding,
            )
        }
    }
}