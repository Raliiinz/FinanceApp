package com.example.financeapp.expenses.state

/**
 * События экрана расходов.
 */
sealed class ExpensesEvent {
    object HideErrorDialog : ExpensesEvent()
    data class LoadExpenses(val from: String, val to: String) : ExpensesEvent()
}