package com.example.financeapp.network.pojo.response.account

/**
 * Модель изменения состояния аккаунта.
 * Фиксирует изменения между двумя состояниями аккаунта.
 */
data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountState,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)
