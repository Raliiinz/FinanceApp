package com.example.financeapp.network.pojo.response.account

/**
 * Ответ API для истории изменений аккаунта.
 * Содержит текущее состояние аккаунта и список изменений.
 */
data class AccountHistoryResponse (
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: AccountHistory
)
