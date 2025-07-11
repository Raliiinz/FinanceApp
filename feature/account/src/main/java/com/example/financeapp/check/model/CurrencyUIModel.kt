package com.example.financeapp.check.model

/**
 * UI-модель валюты.
 * Хранит код валюты, ресурсы названия/иконки и символ.
 */
data class CurrencyUIModel(
    val currency: String = "",
    val nameResId: Int = 0,
    val symbol: String = "",
    val iconResId: Int = 0,
)