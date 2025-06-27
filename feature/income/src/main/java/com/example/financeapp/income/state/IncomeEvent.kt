package com.example.financeapp.income.state

/**
 * События экрана доходов.
 */
sealed class IncomeEvent {
    object HideErrorDialog : IncomeEvent()
    object ReloadData : IncomeEvent()
}