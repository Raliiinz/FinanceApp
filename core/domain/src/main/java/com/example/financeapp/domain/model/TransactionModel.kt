package com.example.financeapp.domain.model

/**
 * Доменная модель транзакции.
 */
data class TransactionModel(
    val id: Int,
    val account: AccountModel,
    val category: CategoryModel,
    val amount: Double,
    val date: String,
    val comment: String?,
    val isIncome: Boolean
)