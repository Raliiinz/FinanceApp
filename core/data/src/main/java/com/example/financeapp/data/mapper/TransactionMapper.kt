package com.example.financeapp.data.mapper

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.network.pojo.response.transaction.TransactionResponse
import javax.inject.Inject
import javax.inject.Singleton


/*Маппер для работы с модельками транзакций*/
@Singleton
class TransactionMapper @Inject constructor() {
    fun toDomain(response: TransactionResponse): TransactionModel = TransactionModel(
        id = response.id,
        accountId = response.account.id.toString(),
        categoryEmoji = response.category.emoji,
        categoryName = response.category.name,
        isIncome = response.category.isIncome,
        amount = response.amount.toDoubleOrNull() ?: 0.0,
        time = response.transactionDate,
        comment = response.comment
    )
}
