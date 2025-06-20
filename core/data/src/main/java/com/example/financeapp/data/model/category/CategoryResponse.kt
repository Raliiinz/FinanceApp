package com.example.financeapp.data.model.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("isIncome") val isIncome: Boolean
)
