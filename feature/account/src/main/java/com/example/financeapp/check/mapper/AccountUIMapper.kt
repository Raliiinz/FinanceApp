package com.example.financeapp.check.mapper

import com.example.financeapp.base.ui.util.extension.formatAmount
import com.example.financeapp.check.model.AccountUIModel
import com.example.financeapp.domain.model.AccountModel
import javax.inject.Inject

class AccountUIMapper @Inject constructor(
    private val currencyUIMapper: CurrencyUIMapper
) {
    fun map(account: AccountModel): AccountUIModel {
        return AccountUIModel(
            id = account.id,
            name = account.name,
            balance = account.balance.formatAmount(),
            currency = currencyUIMapper.map(account.currency)
        )
    }
}