package com.example.financeapp.network.pojo.response.account

/**
 * Модель состояния аккаунта в конкретный момент времени.
 * Используется для хранения истории изменений.
 */
data class AccountState (
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
