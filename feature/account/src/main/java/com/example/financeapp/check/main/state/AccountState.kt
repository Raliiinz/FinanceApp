package com.example.financeapp.check.main.state

import androidx.compose.runtime.Immutable
import com.example.financeapp.check.model.AccountUIModel

@Immutable
data class AccountState(
    val isLoading: Boolean = false,
    val account: AccountUIModel = AccountUIModel(),
    val showErrorDialog: Int? = null
)