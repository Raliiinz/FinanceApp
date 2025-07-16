package com.example.financeapp.data.local.database.dao

import androidx.room.*
import com.example.financeapp.data.local.database.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE deleted = 0 ORDER BY transaction_date DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE local_id = :id OR remote_id = :id LIMIT 1")
    suspend fun getById(id: Int): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE account_id = :accountId AND transaction_date BETWEEN :from AND :to")
    suspend fun deleteByAccountAndPeriod(accountId: Int, from: String, to: String)

    @Query("SELECT * FROM transactions WHERE account_id = :accountId AND transaction_date BETWEEN :from AND :to AND deleted = 0 ORDER BY transaction_date DESC")
    suspend fun getByAccountAndPeriod(accountId: Int, from: String, to: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE is_synced = 0 OR deleted = 1")
    suspend fun getUnsynced(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE remote_id = :remoteId LIMIT 1")
    suspend fun getByRemoteId(remoteId: Int): TransactionEntity?
}