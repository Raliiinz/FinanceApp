package com.example.financeapp.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.settings.components.SettingsList

/**
 * Главный Composable экрана настроек.
 */
@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    onSettingClicked: (Int) -> Unit,
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SettingsList(
            settings = uiState.settings,
            isDarkThemeEnabled = uiState.isDarkThemeEnabled,
            paddingValues = paddingValues,
            onThemeToggle = { viewModel.toggleDarkTheme() },
            onSettingClicked = onSettingClicked
        )
    }
}
