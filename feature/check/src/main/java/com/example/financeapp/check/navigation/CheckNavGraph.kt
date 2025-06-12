package com.example.financeapp.check.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.check.screen.CheckScreenViewModel
import com.example.financeapp.check.screen.ChecksScreen
import com.example.financeapp.navigation.Screen

fun NavGraphBuilder.checkNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "check/main",
        route = Screen.Check.route
    ) {
        composable("check/main") {
            ChecksRoute(
                paddingValues = paddingValues,
                onCheckClicked = { id -> /* handle tap */ },
                onFabClick = { /* handle FAB */ }
            )
        }
    }
}


@Composable
fun ChecksRoute(
    paddingValues: PaddingValues,
    onCheckClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: CheckScreenViewModel = hiltViewModel()
) {
    val checkList by viewModel.checksList.collectAsState()

    ChecksScreen(
        checkList = checkList,
        paddingValues = paddingValues,
        onCheckClicked = onCheckClicked,
        onFabClick = onFabClick
    )
}