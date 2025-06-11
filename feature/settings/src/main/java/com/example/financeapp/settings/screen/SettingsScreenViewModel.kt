package com.example.financeapp.settings.screen

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsScreenViewModel : ViewModel() {

    private val initialSettingsList = listOf(
        Settings(0, R.string.main_color, R.drawable.triangle),
        Settings(1, R.string.sounds, R.drawable.triangle),
        Settings(2, R.string.haptics, R.drawable.triangle),
        Settings(3, R.string.password, R.drawable.triangle),
        Settings(4, R.string.synchronizing, R.drawable.triangle),
        Settings(5, R.string.language, R.drawable.triangle),
        Settings(6, R.string.about_the_program, R.drawable.triangle),
    )

    private val _settingsList = MutableStateFlow(initialSettingsList)
    val settingsList: StateFlow<List<Settings>> = _settingsList
}