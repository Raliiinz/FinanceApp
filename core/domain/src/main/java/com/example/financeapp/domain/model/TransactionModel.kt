package com.example.financeapp.domain.model

/**
 * Доменная модель транзакции.
 */
data class TransactionModel(
    val id: Int,
    val accountId: String,
    val currency: String,
    val categoryName: String,
    val categoryEmoji: String,
    val isIncome: Boolean,
    val amount: Double,
    val time: String,
    val comment: String?
)