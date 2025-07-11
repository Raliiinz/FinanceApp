package com.example.financeapp.network.pojo.request.transaction

import com.google.gson.annotations.SerializedName

data class TransactionRequest(
    @SerializedName("accountId") val accountId: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("amount") val amount: String,
    @SerializedName("transactionDate") val transactionDate: String,
    @SerializedName("comment") val comment: String?
)