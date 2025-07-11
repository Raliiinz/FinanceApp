package com.example.financeapp.transaction.model

/**
 * UI-модель аккаунта, используемая при создании и редактировании транзакции.
 *
 * @param id Уникальный идентификатор аккаунта.
 * @param name Название аккаунта.
 * @param currency Код валюты (например, USD, EUR).
 */
data class TransactionFormAccountUI(
    val id: Int,
    val name: String,
    val currency: String
)