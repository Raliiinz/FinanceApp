package com.example.financeapp.settings.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.financeapp.base.di.ViewModelFactory
import com.example.financeapp.navigation.Screen
import com.example.financeapp.settings.screen.SettingsScreen
import com.example.financeapp.settings.screen.SettingsViewModel

/**
 * Настройка графа навигации для экрана настроек
 */
fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    navigation(
        startDestination = "settings/main",
        route =  Screen.Settings.route
    ) {
        composable("settings/main") { backStackEntry ->
            SettingsRoute(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = viewModel(
                    factory = viewModelFactory,
                    viewModelStoreOwner = backStackEntry
                )
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
    viewModel: SettingsViewModel,
) {
    SettingsScreen(
        viewModel = viewModel,
        paddingValues = paddingValues,
        onSettingClicked = { settingId ->
            // навигация на подэкран, если нужно
        }
    )
}
