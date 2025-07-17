package com.example.financeapp.network.pojo.response.transaction

import com.google.gson.annotations.SerializedName

data class TransactionCreateResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("accountId") val accountId: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("amount") val amount: String,
    @SerializedName("transactionDate") val transactionDate: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)