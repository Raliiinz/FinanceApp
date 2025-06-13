package com.example.financeapp.income.state

import com.example.financeapp.domain.model.Income

sealed class IncomeUiState {
    object Loading : IncomeUiState()
    data class Success(val incomes: List<Income>) : IncomeUiState()
    data class Error(val message: String) : IncomeUiState()
    object Empty : IncomeUiState()
}