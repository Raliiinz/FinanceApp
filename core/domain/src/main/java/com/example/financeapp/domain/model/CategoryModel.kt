package com.example.financeapp.domain.model

/**
 * Доменная модель категории.
 */
data class CategoryModel(
    val id: Int,
    val iconLeading: String,
    val textLeading: String,
    val isIncome: Boolean
)