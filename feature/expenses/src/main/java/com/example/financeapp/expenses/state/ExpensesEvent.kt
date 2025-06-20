package com.example.financeapp.expenses.state

sealed class ExpensesEvent {
    object HideErrorDialog : ExpensesEvent()
    data class LoadExpenses(val from: String, val to: String) : ExpensesEvent()
}