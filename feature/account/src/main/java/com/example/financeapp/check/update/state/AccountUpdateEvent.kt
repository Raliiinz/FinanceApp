package com.example.financeapp.check.update.state

import com.example.financeapp.check.model.CurrencyUIModel

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