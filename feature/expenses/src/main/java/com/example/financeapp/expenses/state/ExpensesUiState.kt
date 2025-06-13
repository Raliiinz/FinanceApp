package com.example.financeapp.expenses.state

import com.example.financeapp.domain.model.Expense

sealed class ExpensesUiState {
    object Loading : ExpensesUiState()
    data class Success(val expenses: List<Expense>) : ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()
    object Empty : ExpensesUiState()
}