package com.example.financeapp.domain.repository.local

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.util.result.Result

interface TransactionLocalRepository {
    suspend fun saveTransactions(transactions: List<TransactionModel>)
    suspend fun deleteTransaction(id:Int)
    suspend fun updateTransaction(transaction: TransactionModel)
    suspend fun addTransaction(transaction: TransactionModel,isSynced: Boolean)
    suspend fun getUnsyncedTransactions(): List<TransactionModel>
    suspend fun markAsSynced(id: Int)
    suspend fun getTransactionById(id: Int): TransactionModel
    suspend fun getCachedTransactions(accountId:Int, from: String, to: String): List<TransactionModel>
}