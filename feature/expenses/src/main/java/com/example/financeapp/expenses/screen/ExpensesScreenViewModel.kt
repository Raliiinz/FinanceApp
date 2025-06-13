package com.example.financeapp.expenses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.usecase.GetExpenseUseCase
import com.example.financeapp.expenses.state.ExpensesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _uiState.value = ExpensesUiState.Loading
            try {
                val expenses = getExpenseUseCase()
                _uiState.value = if (expenses.isNullOrEmpty()) {
                    ExpensesUiState.Empty
                } else {
                    ExpensesUiState.Success(expenses)
                }
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}