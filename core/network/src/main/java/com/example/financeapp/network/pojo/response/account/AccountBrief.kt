package com.example.financeapp.network.pojo.response.account

import com.google.gson.annotations.SerializedName

/**
 * Краткая модель аккаунта для API запросов.
 * Содержит только основные поля для отображения и идентификации.
 */
data class AccountBrief (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("currency") val currency: String
)
