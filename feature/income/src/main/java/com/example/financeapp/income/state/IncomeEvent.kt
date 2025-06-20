package com.example.financeapp.income.state

sealed class IncomeEvent {
    object HideErrorDialog : IncomeEvent()
    object ReloadData : IncomeEvent()
}