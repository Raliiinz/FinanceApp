package com.example.financeapp.network.pojo.response.account

import com.google.gson.annotations.SerializedName

/**
 * Полная модель аккаунта из API.
 * Содержит все поля аккаунта, включая системные (createdAt, updatedAt).
 */
data class Account (
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
