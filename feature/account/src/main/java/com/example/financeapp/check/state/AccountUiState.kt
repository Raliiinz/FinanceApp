package com.example.financeapp.check.state

import androidx.annotation.StringRes
import com.example.financeapp.domain.model.AccountModel

/**
 * Состояния UI экрана счета.
 */
sealed class AccountUiState {
    object Loading : AccountUiState()
    data class Success(val account: AccountModel) : AccountUiState()
    data class Error(@StringRes val messageRes: Int) : AccountUiState()
    object Empty : AccountUiState()
    object Idle : AccountUiState()
}
