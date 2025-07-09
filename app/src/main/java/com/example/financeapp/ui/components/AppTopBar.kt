package com.example.financeapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.financeapp.base.R
import com.example.financeapp.base.ui.commonItems.TopBarTextIcon
import com.example.financeapp.check.navigation.CheckRoutes
import com.example.financeapp.domain.model.TopBarConfig
import com.example.financeapp.extensions.isArticlesScreen
import com.example.financeapp.extensions.isCheckScreen
import com.example.financeapp.extensions.isExpensesScreen
import com.example.financeapp.extensions.isHistoryScreen
import com.example.financeapp.extensions.isIncomeScreen
import com.example.financeapp.extensions.isSettingsScreen
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.navigation.TransactionType

/**
 * Верхняя панель приложения с динамическим контентом в зависимости от текущего экрана.
 */
@Composable
fun AppTopBar(
    currentDestination: NavDestination?,
    navController: NavController,
    historyNavigation: HistoryNavigation
) {

    val topBarContent = remember(currentDestination) {
        when {
            currentDestination?.isExpensesScreen() == true -> TopBarConfig(
                textResId = R.string.expenses_today,
                trailingImageResId = R.drawable.refresh,
                onTrailingClick = {
                    navController.navigate(historyNavigation.navigateToHistory(TransactionType.EXPENSE))
                }
            )

            currentDestination?.isIncomeScreen() == true -> TopBarConfig(
                textResId = R.string.income_today,
                trailingImageResId = R.drawable.refresh,
                onTrailingClick = {
                    navController.navigate(historyNavigation.navigateToHistory(TransactionType.INCOME))
                }
            )

            currentDestination?.isSettingsScreen() == true -> TopBarConfig(R.string.settings)
            currentDestination?.isArticlesScreen() == true -> TopBarConfig(R.string.my_articles)
            currentDestination?.isCheckScreen() == true -> TopBarConfig(
                textResId = R.string.my_check,
                trailingImageResId = R.drawable.pencil,
                onTrailingClick = {
                    navController.navigate(CheckRoutes.CHECK_EDIT_FROM_TOPBAR)
                }
            )

            currentDestination?.isHistoryScreen() == true -> TopBarConfig(
                textResId = R.string.history,
                leadingImageResId = R.drawable.ic_back,
                trailingImageResId = R.drawable.ic_analysis,
                onLeadingClick = { navController.popBackStack() }
            )

            else -> null
        }
    }

    topBarContent?.let { config ->
        TopBarTextIcon(
            textResId = config.textResId,
            leadingImageResId = config.leadingImageResId,
            trailingImageResId = config.trailingImageResId,
            onLeadingClicked = config.onLeadingClick,
            onTrailingClicked = config.onTrailingClick
        )
    }
}
