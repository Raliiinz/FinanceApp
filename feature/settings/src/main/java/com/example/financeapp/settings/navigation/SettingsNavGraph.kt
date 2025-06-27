package com.example.financeapp.settings.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.navigation.Screen
import com.example.financeapp.settings.screen.SettingsScreen
import com.example.financeapp.settings.screen.SettingsViewModel

/**
 * Настройка графа навигации для экрана настроек
 */
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
                paddingValues = paddingValues
            )
        }
    }
}

/**
 * Основной маршрут экрана настроек.
 */
@Composable
fun SettingsRoute(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    SettingsScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onSettingClicked = { settingId ->
            // навигация на подэкран, если нужно
        }
    )
}
