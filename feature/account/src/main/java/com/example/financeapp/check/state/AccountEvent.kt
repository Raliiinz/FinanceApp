package com.example.financeapp.check.state

sealed class AccountEvent {
    object Retry : AccountEvent()
    object HideErrorDialog : AccountEvent()
}