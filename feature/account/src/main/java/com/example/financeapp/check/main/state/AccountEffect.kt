package com.example.financeapp.check.main.state

/**
 * События экрана счета.
 */
sealed class AccountEffect {
    object Retry : AccountEffect()
    object HideErrorDialog : AccountEffect()
}
