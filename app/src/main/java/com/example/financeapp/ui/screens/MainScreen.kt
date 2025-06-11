package com.example.financeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.base.R
import androidx.compose.runtime.getValue
import com.example.financeapp.domain.model.ScaffoldItem
import com.example.financeapp.navigation.NavigationItem
import com.example.financeapp.navigation.NavigationState
import com.example.financeapp.navigation.RootNavGraph
import com.example.financeapp.navigation.Screen
import com.example.financeapp.navigation.rememberNavigationState

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
@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        NavigationItem.all.forEach { item ->
            val selected = currentRoute
                ?.hierarchy
                ?.any { it.route == item.screen.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(id = item.titleResId),
                        tint = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.titleResId),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },

                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}


