package com.example.financeapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financeapp.analysis.navigation.di.AnalysisComponent
import com.example.financeapp.articles.di.CategoryComponent
import com.example.financeapp.check.di.AccountComponent
import com.example.financeapp.expenses.di.ExpensesComponent
import com.example.financeapp.history.di.HistoryComponent
import com.example.financeapp.income.di.IncomeComponent
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.settings.di.SettingsComponent
import com.example.financeapp.transaction.di.TransactionComponent
import com.example.financeapp.ui.components.SplashScreenWithNavigation

/**
 * Главная Activity приложения.
 * Отвечает за отображение splash screen и основного экрана приложения.
 */

class MainActivity : ComponentActivity() {
    private lateinit var expensesComponent: ExpensesComponent
    private lateinit var historyComponent: HistoryComponent
    private lateinit var incomeComponent: IncomeComponent
    private lateinit var settingsComponent: SettingsComponent
    private lateinit var categoryComponent: CategoryComponent
    private lateinit var accountComponent: AccountComponent
    private lateinit var transactionComponent: TransactionComponent
    private lateinit var analysisComponent: AnalysisComponent
    private lateinit var historyNavigationInstance: HistoryNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = FinanceApplication.instance.appComponent
        historyNavigationInstance = appComponent.historyNavigation()

        expensesComponent = appComponent.expensesComponentBuilder().build()
        accountComponent = appComponent.checkComponentBuilder().build()
        categoryComponent = appComponent.articlesComponentBuilder().build()
        historyComponent = appComponent.historyComponentBuilder().build()
        incomeComponent = appComponent.incomeComponentBuilder().build()
        settingsComponent = appComponent.settingsComponentBuilder().build()
        transactionComponent = appComponent.transactionComponentBuilder().build()
        analysisComponent = appComponent.analysisComponentBuilder().build()


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            SplashScreenWithNavigation(
                historyNavigation = historyNavigationInstance,
                expensesViewModelFactory = expensesComponent.getViewModelFactory(),
                historyViewModelFactory = historyComponent.getViewModelFactory(),
                incomeViewModelFactory = incomeComponent.getViewModelFactory(),
                articlesViewModelFactory = categoryComponent.getViewModelFactory(),
                settingsViewModelFactory = settingsComponent.getViewModelFactory(),
                checkViewModelFactory = accountComponent.getViewModelFactory(),
                analysisViewModelFactory = analysisComponent.getViewModelFactory()
            )
        }
    }
}
