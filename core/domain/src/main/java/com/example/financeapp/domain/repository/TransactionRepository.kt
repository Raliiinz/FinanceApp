package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.util.result.Result

/**
 * репозиторий для данных о транзакциях
 */
interface TransactionRepository {
    suspend fun getTransactions(accountId: Int, from: String, to: String): Result<List<TransactionModel>>
    suspend fun createTransaction(transaction: TransactionModel)
    suspend fun updateTransaction(transaction: TransactionModel)
    suspend fun deleteTransaction(id: Int)
}