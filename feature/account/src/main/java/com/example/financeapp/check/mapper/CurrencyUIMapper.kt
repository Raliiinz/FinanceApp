package com.example.financeapp.check.mapper

import com.example.financeapp.base.ui.util.extension.toCurrencyIconResId
import com.example.financeapp.base.ui.util.extension.toCurrencyNameResId
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.check.model.CurrencyUIModel
import javax.inject.Inject

class CurrencyUIMapper @Inject constructor() {
    fun map(currency: String): CurrencyUIModel {
        return CurrencyUIModel(
            currency = currency,
            nameResId = currency.toCurrencyNameResId(),
            symbol = currency.toCurrencySymbol(),
            iconResId = currency.toCurrencyIconResId()
        )
    }

    fun mapList(currencies: List<String>): List<CurrencyUIModel> {
        return currencies.map { map(it) }
    }
}