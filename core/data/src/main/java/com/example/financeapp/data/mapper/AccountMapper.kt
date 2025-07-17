package com.example.financeapp.data.mapper

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.local.database.entity.AccountEntity
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.network.pojo.response.account.Account
import com.example.financeapp.network.pojo.response.account.AccountBrief
import javax.inject.Inject

/**
 * Маппер для преобразования между сетевыми и доменными моделями аккаунтов.
 */
@AppScope
class AccountMapper @Inject constructor() {
    fun toDomain(response: Account): AccountModel = AccountModel(
        id = response.id,
        name = response.name,
        balance = response.balance.toDouble(),
        currency = response.currency
    )

    fun briefToDomain(response: AccountBrief): AccountModel = AccountModel(
        id = response.id,
        name = response.name,
        balance = response.balance.toDouble(),
        currency = response.currency
    )

    fun entityToDomain(response: AccountEntity): AccountModel = AccountModel(
        id = response.remoteId ?: response.localId,
        name = response.name,
        balance = response.balance.toDoubleOrNull() ?: 0.0,
        currency = response.currency,
    )

    fun toEntity(request: Account): AccountEntity = AccountEntity(
        remoteId = request.id,
        userId = 20,
        name = request.name,
        balance = request.balance.toString(),
        currency = request.currency,
        createdAt = "",
        updatedAt = "",
        isSynced = false,
        lastSyncedAt = null
    )
}
