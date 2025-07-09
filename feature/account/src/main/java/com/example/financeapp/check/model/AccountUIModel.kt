package com.example.financeapp.check.model

data class AccountUIModel(
    val id: Int = 0,
    val name: String = "",
    val balance: String = "0.00 ₽",
    val currency: CurrencyUIModel = CurrencyUIModel(),
)