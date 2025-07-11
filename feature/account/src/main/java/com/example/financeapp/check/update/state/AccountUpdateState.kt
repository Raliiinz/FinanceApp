package com.example.financeapp.check.update.state

import androidx.compose.runtime.Immutable
import com.example.financeapp.check.model.AccountUIModel
import com.example.financeapp.check.model.CurrencyUIModel

/**
 * Состояние экрана редактирования аккаунта.
 * Хранит данные аккаунта, статус загрузки и видимость UI-элементов.
 */
@Immutable
data class AccountUpdateState(
    val isLoading: Boolean = false,
    val account: AccountUIModel = AccountUIModel(),
    val currencies: List<CurrencyUIModel> = emptyList(),
    val showErrorDialog: Int? = null,
    val showBottomSheet: Boolean = false
)