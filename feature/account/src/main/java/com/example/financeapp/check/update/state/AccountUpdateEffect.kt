package com.example.financeapp.check.update.state

/**
 * Эффекты экрана редактирования аккаунта.
 * Содержит события навигации и показа уведомлений.
 */
sealed class AccountUpdateEffect {
    object AccountUpdated : AccountUpdateEffect()
    data class ShowToast(val messageResId: Int) : AccountUpdateEffect()
}