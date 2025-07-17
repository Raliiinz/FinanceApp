package com.example.financeapp.domain.model

data class AnalyticsCategory(
    val categoryName: String,
    val categoryIcon: String,
    val totalAmount: Double,
    val percent: Int
)