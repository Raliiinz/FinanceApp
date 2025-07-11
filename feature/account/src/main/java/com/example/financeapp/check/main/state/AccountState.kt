package com.example.financeapp.check.main.state

import androidx.compose.runtime.Immutable
import com.example.financeapp.check.model.AccountUIModel

/**
 * Состояние экрана аккаунта.
 * Хранит статус загрузки, данные аккаунта и флаг ошибки.
 */
@Immutable
data class AccountState(
    val isLoading: Boolean = false,
    val account: AccountUIModel = AccountUIModel(),
    val showErrorDialog: Int? = null
)