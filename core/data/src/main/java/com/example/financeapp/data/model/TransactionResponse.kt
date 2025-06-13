package com.example.financeapp.data.model

data class TransactionResponse (
    val id: Int,
    val account: AccountBrief,
    val category: CategoryResponse,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)