package com.example.financeapp.data.model.account

import com.example.financeapp.data.model.category.StatItem

data class AccountResponse (
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: StatItem,
    val expenseStats: StatItem,
    val createdAt: String,
    val updatedAt: String
)