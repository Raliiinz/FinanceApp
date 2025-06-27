package com.example.financeapp.check.state

/**
 * События экрана счета.
 */
sealed class AccountEvent {
    object Retry : AccountEvent()
    object HideErrorDialog : AccountEvent()
}
