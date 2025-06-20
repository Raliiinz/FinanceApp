package com.example.financeapp.settings.screen

import androidx.lifecycle.ViewModel
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.SettingsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
//    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val initialSettingsList = listOf(
        SettingsModel(0, R.string.main_color, R.drawable.ic_arrow_right),
        SettingsModel(1, R.string.sounds, R.drawable.ic_arrow_right),
        SettingsModel(2, R.string.haptics, R.drawable.ic_arrow_right),
        SettingsModel(3, R.string.password, R.drawable.ic_arrow_right),
        SettingsModel(4, R.string.synchronizing, R.drawable.ic_arrow_right),
        SettingsModel(5, R.string.language, R.drawable.ic_arrow_right),
        SettingsModel(6, R.string.about_the_program, R.drawable.ic_arrow_right),
    )

    private val _settingsList = MutableStateFlow(initialSettingsList)
    val settingsList: StateFlow<List<SettingsModel>> = _settingsList.asStateFlow()

    private val _isDarkThemeEnabled = MutableStateFlow(false)
    val isDarkThemeEnabled: StateFlow<Boolean> = _isDarkThemeEnabled.asStateFlow()

//    init {
//        viewModelScope.launch {
//            settingsRepository.isDarkThemeEnabled().collectLatest { isEnabled ->
//                _isDarkThemeEnabled.value = isEnabled
//            }
//        }
//    }
//
//    // Метод для переключения состояния темы
//    fun toggleDarkTheme() {
//        viewModelScope.launch {
//            settingsRepository.setDarkThemeEnabled(!_isDarkThemeEnabled.value)
//        }
//    }
//
//    // Метод для обработки клика по элементу списка
//    fun onSettingItemClick(settingId: Int) {
//        when (settingId) {
//            0 -> {  }
//            1 -> {  }
//            // и т.д.
//        }
//    }
}