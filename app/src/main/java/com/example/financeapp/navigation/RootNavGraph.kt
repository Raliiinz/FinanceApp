package com.example.financeapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.financeapp.articles.navigation.articlesNavGraph
import com.example.financeapp.check.navigation.checkNavGraph
import com.example.financeapp.expenses.navigation.expensesNavGraph
import com.example.financeapp.history.navigation.historyNavGraph
import com.example.financeapp.income.navigation.incomeNavGraph
import com.example.financeapp.settings.navigation.settingsNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,

) {
    NavHost(
        navController = navController,
        startDestination = Screen.Expenses.route,
    ) {
        expensesNavGraph(navController, paddingValues)
        incomeNavGraph(navController, paddingValues,)
        checkNavGraph(navController, paddingValues)
        articlesNavGraph(navController, paddingValues)
        settingsNavGraph(navController, paddingValues)
        historyNavGraph(navController, paddingValues)
    }
}