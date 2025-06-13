package com.example.financeapp.income.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.usecase.GetIncomeUseCase
import com.example.financeapp.income.state.IncomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<IncomeUiState>(IncomeUiState.Loading)
    val uiState: StateFlow<IncomeUiState> = _uiState.asStateFlow()

    init {
        loadIncomes()
    }

    fun loadIncomes() {
        viewModelScope.launch {
            _uiState.value = IncomeUiState.Loading
            try {
                val incomes = getIncomeUseCase()
                _uiState.value = if (incomes.isNullOrEmpty()) {
                    IncomeUiState.Empty
                } else {
                    IncomeUiState.Success(incomes)
                }
            } catch (e: Exception) {
                _uiState.value = IncomeUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}