package com.example.financeapp.settings.state

import com.example.financeapp.base.R
import com.example.financeapp.domain.model.SettingsModel

/**
 * Состояние экрана настроек.
 * Ответственность: Хранение данных для отображения на экране настроек.
 */
data class SettingsState(
    val settings: List<SettingsModel> = defaultSettings,
    val isDarkThemeEnabled: Boolean = false
)

private val defaultSettings = listOf(
    SettingsModel(0, R.string.main_color, R.drawable.ic_arrow_right),
    SettingsModel(1, R.string.sounds, R.drawable.ic_arrow_right),
    SettingsModel(2, R.string.haptics, R.drawable.ic_arrow_right),
    SettingsModel(3, R.string.password, R.drawable.ic_arrow_right),
    SettingsModel(4, R.string.synchronizing, R.drawable.ic_arrow_right),
    SettingsModel(5, R.string.language, R.drawable.ic_arrow_right),
    SettingsModel(6, R.string.about_the_program, R.drawable.ic_arrow_right)
)
