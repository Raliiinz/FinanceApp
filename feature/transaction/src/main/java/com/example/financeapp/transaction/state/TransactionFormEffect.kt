package com.example.financeapp.transaction.state

import androidx.annotation.StringRes

/**
 * Эффекты (одноразовые события), отправляемые из ViewModel к UI.
 */
sealed class TransactionFormEffect {
    data class ShowToast(@StringRes val messageResId: Int) : TransactionFormEffect()
    object TransactionSaved : TransactionFormEffect()
    object TransactionDeleted : TransactionFormEffect()
}