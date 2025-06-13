package com.example.financeapp.data.mapper

import com.example.financeapp.data.model.AccountResponse
import com.example.financeapp.data.model.CategoryResponse
import com.example.financeapp.data.model.TransactionResponse
import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.model.Income

fun TransactionResponse.mapToExpenses(): Expense{
    return Expense(
        id = this.id,
        iconLeading = this.category.emoji,
        textLeading = this.category.name,
        commentLeading = this.comment,
        priceTrailing = this.amount.toDouble()
    )
}
fun TransactionResponse.mapToIncome(): Income{
    return Income(
        id = this.id,
        textLeading = this.category.name,
        priceTrailing = this.amount.toDouble()
    )
}

fun AccountResponse.mapToCheck(): Check{
    return Check(
        id = this.id,
        leadingIcon = this.incomeStats.emoji,
        balance = this.balance.toDouble(),
        currency = this.currency
    )
}

fun CategoryResponse.mapToCategory(): Category{
    return Category(
        id = this.id,
        iconLeading = this.emoji,
        textLeading = this.name
    )
}