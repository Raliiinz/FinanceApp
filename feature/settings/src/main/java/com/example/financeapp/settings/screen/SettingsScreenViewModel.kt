package com.example.financeapp.settings.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана настроек.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun toggleDarkTheme() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isDarkThemeEnabled = !_state.value.isDarkThemeEnabled
            )
        }
    }
}
