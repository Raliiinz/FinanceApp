package com.example.financeapp.ui.screens

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
import com.example.financeapp.domain.model.ScaffoldItem
import com.example.financeapp.navigation.RootNavGraph
import com.example.financeapp.navigation.Screen
import com.example.financeapp.ui.components.BottomNavBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    val topBarContent = when {
        destination?.hierarchy?.any { it.route == Screen.Expenses.route } == true -> {
            ScaffoldItem(R.string.expenses_today, R.drawable.refresh)
        }
        destination?.hierarchy?.any { it.route == Screen.Settings.route } == true -> {
            ScaffoldItem(R.string.settings, null)
        }
        destination?.hierarchy?.any { it.route == Screen.Articles.route } == true -> {
            ScaffoldItem(R.string.my_articles, null)
        }
        destination?.hierarchy?.any { it.route == Screen.Income.route } == true -> {
            ScaffoldItem(R.string.income_today, R.drawable.refresh)
        }
        destination?.hierarchy?.any { it.route == Screen.Check.route } == true -> {
            ScaffoldItem(R.string.my_check, R.drawable.pencil)
        }
        else -> null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            topBarContent?.let {
                TopBarTextIcon(it.textResId, it.imageResId, onClick = {})
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            RootNavGraph(
                navController = navController,
                paddingValues = innerPadding
            )
        }
    }
}
