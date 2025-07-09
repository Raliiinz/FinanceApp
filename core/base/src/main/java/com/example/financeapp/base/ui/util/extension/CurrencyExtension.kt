package com.example.financeapp.base.ui.util.extension

import com.example.financeapp.base.R
import java.util.Locale

fun String.toCurrencySymbol(): String {
    return when (this) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this
    }
}

fun String.toCurrencyNameResId(): Int {
    return when (this) {
        "RUB" -> R.string.currency_rub
        "USD" -> R.string.currency_usd
        "EUR" -> R.string.currency_eur
        else -> R.string.currency_unknown
    }
}

fun String.toCurrencyIconResId(): Int {
    return when (this) {
        "RUB" -> R.drawable.ic_ruble
        "USD" -> R.drawable.ic_dollar
        "EUR" -> R.drawable.ic_euro
        else -> R.string.currency_unknown
    }
}

fun Double.formatAmount(locale: Locale = Locale.US): String {
    return String.format(locale, "%.2f", this)
}