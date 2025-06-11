package com.example.financeapp.domain.model

data class Expense (
    val id: Int,
    val iconLeading: String,
    val textLeading: String,
    val commentLeading: String?,
    val priceTrailing: Double
)