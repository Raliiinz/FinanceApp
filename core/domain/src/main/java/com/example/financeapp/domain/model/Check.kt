package com.example.financeapp.domain.model

data class Check(
    val id: Int,
    val leadingIcon: String,
    val balance: Double,
    val currency: String
)