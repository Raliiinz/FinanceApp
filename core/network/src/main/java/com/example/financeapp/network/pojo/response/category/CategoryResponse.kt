package com.example.financeapp.network.pojo.response.category

import com.google.gson.annotations.SerializedName

/**
 * Модель ответа API для категории.
 * Содержит основные данные категории, включая признак доход/расход.
 */
data class CategoryResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("isIncome") val isIncome: Boolean
)
