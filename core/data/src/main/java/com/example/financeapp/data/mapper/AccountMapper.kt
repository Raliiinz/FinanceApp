package com.example.financeapp.data.mapper

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.network.pojo.response.account.Account
import com.example.financeapp.network.pojo.response.account.AccountBrief
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Маппер для преобразования между сетевыми и доменными моделями аккаунтов.
 */
@AppScope
class AccountMapper @Inject constructor() {
    fun toBriefAccount(model: AccountModel): AccountBrief = AccountBrief(
        id = model.id.toInt(),
        name = model.name,
        balance = model.balance.toString(),
        currency = model.currency
    )

    fun toDomain(response: Account): AccountModel = AccountModel(
        id = response.id,
        name = response.name,
        balance = response.balance.toDouble(),
        currency = response.currency
    )
}
