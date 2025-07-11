package com.example.financeapp.check.update.state

import com.example.financeapp.check.model.CurrencyUIModel

/**
 * События экрана редактирования аккаунта.
 * Включает действия пользователя и системные события.
 */
sealed class AccountUpdateEvent {
    object LoadData : AccountUpdateEvent()
    object OnDoneClicked : AccountUpdateEvent()
    object HideErrorDialog : AccountUpdateEvent()
    object HideCurrencyBottomSheet : AccountUpdateEvent()
    object ShowCurrencyBottomSheet : AccountUpdateEvent()
    data class SelectCurrency(val currency: CurrencyUIModel) : AccountUpdateEvent()
    data class UpdateName(val name: String) : AccountUpdateEvent()
    data class UpdateBalance(val balance: String) : AccountUpdateEvent()
}