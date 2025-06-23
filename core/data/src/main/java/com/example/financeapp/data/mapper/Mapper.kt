package com.example.financeapp.data.mapper

import com.example.financeapp.data.model.account.Account
import com.example.financeapp.data.model.account.AccountBrief
import com.example.financeapp.data.model.category.CategoryResponse
import com.example.financeapp.data.model.transaction.TransactionResponse
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.model.TransactionModel

fun TransactionResponse.toDomain(): TransactionModel = TransactionModel(
    id = id,
    accountId = account.id.toString(),
    categoryEmoji = category.emoji,
    categoryName = category.name,
    isIncome = category.isIncome,
    amount = amount.toDoubleOrNull() ?: 0.0,
    time = transactionDate,
    comment = comment
)

fun AccountModel.toAccountBrief(): AccountBrief = AccountBrief(
    id = id.toInt(),
    name = name,
    balance = balance.toString(),
    currency = currency
)

fun Account.toDomain(): AccountModel = AccountModel(
    id = this.id,
    name = this.name,
    balance = this.balance.toDouble(),
    currency = this.currency
)

fun CategoryResponse.mapToCategory(): CategoryModel{
    return CategoryModel(
        id = this.id,
        iconLeading = this.emoji,
        textLeading = this.name,
        isIncome = this.isIncome
    )
}