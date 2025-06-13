package com.example.financeapp.check.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.check.state.CheckUiState
import com.example.financeapp.domain.usecase.GetCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckScreenViewModel @Inject constructor(
    private val getCheckUseCase: GetCheckUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CheckUiState>(CheckUiState.Loading)
    val uiState: StateFlow<CheckUiState> = _uiState.asStateFlow()

    init {
        loadCheck()
    }

    private fun loadCheck() {
        viewModelScope.launch {
            _uiState.value = CheckUiState.Loading
            try {
                val check = getCheckUseCase()
                _uiState.value = if (check != null) {
                    CheckUiState.Success(check)
                } else {
                    CheckUiState.Empty
                }
            } catch (e: Exception) {
                _uiState.value = CheckUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}