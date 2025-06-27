package com.example.financeapp.settings.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.financeapp.domain.model.SettingsModel

/**
 * Компонент списка настроек.
 */
@Composable
fun SettingsList(
    settings: List<SettingsModel>,
    isDarkThemeEnabled: Boolean,
    paddingValues: PaddingValues,
    onThemeToggle: () -> Unit,
    onSettingClicked: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        item {
            ThemeSwitchItem(
                isDarkTheme = isDarkThemeEnabled,
                onThemeToggle = onThemeToggle
            )
        }
        items(settings, key = { it.id }) { setting ->
            SettingItem(
                setting = setting,
                onClick = { onSettingClicked(setting.id) }
            )
        }
    }
}
