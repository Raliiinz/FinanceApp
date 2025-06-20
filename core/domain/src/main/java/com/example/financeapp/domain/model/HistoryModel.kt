package com.example.financeapp.domain.model

data class HistoryModel(
    val id: Int,
    val leadingIcon:String,
    val leadingName:String,
    val leadingComment: String?,
    val trailingPrice: Double,
    val trailingTime: String
)