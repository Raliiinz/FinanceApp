package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.util.result.Result

/**
 * Интерфейс репозитория для работы с транзакциями.
 */
interface TransactionRepository {
    suspend fun getTransactions(accountId: Int, from: String, to: String): Result<List<TransactionModel>>
    suspend fun createTransaction(transaction: TransactionModel): Result<TransactionModel>
    suspend fun updateTransaction(transaction: TransactionModel): Result<TransactionModel>
    suspend fun deleteTransaction(id: Int): Result<Unit>
    suspend fun getTransactionById(id: Int): Result<TransactionModel>
}