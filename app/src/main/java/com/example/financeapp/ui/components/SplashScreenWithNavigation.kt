package com.example.financeapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.base.ui.theme.FinanceAppTheme
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.ui.screens.LottieSplashScreen
import com.example.financeapp.ui.screens.MainScreen

/**
 * Компонент для отображения splash screen и последующего перехода на основной экран.
 */
@Composable
fun SplashScreenWithNavigation(
    historyNavigation: HistoryNavigation,
    expensesViewModelFactory: ViewModelFactory,
    historyViewModelFactory: ViewModelFactory,
    incomeViewModelFactory: ViewModelFactory,
    checkViewModelFactory: ViewModelFactory,
    articlesViewModelFactory: ViewModelFactory,
    settingsViewModelFactory: ViewModelFactory,
    transactionViewModelFactory: ViewModelFactory,
) {
    var isSplashFinished by remember { mutableStateOf(false) }

    FinanceAppTheme(dynamicColor = false) {
        if (!isSplashFinished) {
            LottieSplashScreen {
                isSplashFinished = true
            }
        } else {
            MainScreen(
                historyNavigation = historyNavigation,
                expensesViewModelFactory = expensesViewModelFactory,
                historyViewModelFactory = historyViewModelFactory,
                incomeViewModelFactory = incomeViewModelFactory,
                articlesViewModelFactory = articlesViewModelFactory,
                settingsViewModelFactory = settingsViewModelFactory,
                checkViewModelFactory = checkViewModelFactory,
                transactionViewModelFactory = transactionViewModelFactory
            )
        }
    }
}
