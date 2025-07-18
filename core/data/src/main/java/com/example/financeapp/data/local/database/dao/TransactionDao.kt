package com.example.financeapp.data.local.database.dao

import androidx.room.*
import com.example.financeapp.data.local.database.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE account_id = :accountId AND date BETWEEN :from AND :to")
    suspend fun getTransactionsForPeriod(accountId: Int, from: String, to: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE is_synced = 0")
    suspend fun getUnsyncedTransactions(): List<TransactionEntity>

    @Query("UPDATE transactions SET is_synced = 1 WHERE id=:id")
    suspend fun markTransactionAsSynced(id: Int)

    @Query("UPDATE transactions SET is_synced = :status WHERE id = :id")
    suspend fun updateSyncStatus(id: Int, status: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionList(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id:Int): TransactionEntity

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Int)
}