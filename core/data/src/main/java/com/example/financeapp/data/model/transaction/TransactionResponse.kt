package com.example.financeapp.data.model.transaction

import com.example.financeapp.data.model.account.AccountBrief
import com.example.financeapp.data.model.category.CategoryResponse
import com.google.gson.annotations.SerializedName

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