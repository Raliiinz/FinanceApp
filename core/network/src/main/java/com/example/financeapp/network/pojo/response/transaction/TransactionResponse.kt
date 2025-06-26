package com.example.financeapp.network.pojo.response.transaction

import com.example.financeapp.network.pojo.response.account.AccountBrief
import com.example.financeapp.network.pojo.response.category.CategoryResponse
import com.google.gson.annotations.SerializedName

/**
 * Модель ответа API для транзакции.
 * Содержит полные данные о транзакции, включая связанные аккаунт и категорию.
 */
data class TransactionResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("account") val account: AccountBrief,
    @SerializedName("category") val category: CategoryResponse,
    @SerializedName("amount") val amount: String,
    @SerializedName("transactionDate") val transactionDate: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)