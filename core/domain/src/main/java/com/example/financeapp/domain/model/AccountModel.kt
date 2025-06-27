package com.example.financeapp.domain.model

/**
 * Доменная модель аккаунта.
 */
data class AccountModel(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String
)