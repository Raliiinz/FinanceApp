package com.example.financeapp.check.update.state

sealed class AccountUpdateEffect {
    object AccountUpdated : AccountUpdateEffect()
    data class ShowToast(val messageResId: Int) : AccountUpdateEffect()
}