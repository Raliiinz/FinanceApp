package com.example.financeapp.settings.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.navigation.Screen
import com.example.financeapp.settings.screen.SettingScreen
import com.example.financeapp.settings.screen.SettingsScreenViewModel

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "settings/main",
        route =  Screen.Settings.route
    ) {
        composable("settings/main") {
            SettingsRoute(
                navController = navController,
                paddingValues = paddingValues // или пробрасывай из родителя
            )
//            SettingScreen() { }
        }
    }
}
@Composable
fun SettingsRoute(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    SettingScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onSettingClicked = { settingId ->
            // навигация на подэкран, если нужно
        }
    )
}

//@Composable
//fun SettingsScreen() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text("Settings Screen")
//    }
//}