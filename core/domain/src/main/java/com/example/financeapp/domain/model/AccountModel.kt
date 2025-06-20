package com.example.financeapp.domain.model

data class AccountModel(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String
)