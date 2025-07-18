package com.example.financeapp.data.local.database.dao

import androidx.room.*
import com.example.financeapp.data.local.database.entity.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>)

    @Query("SELECT * FROM accounts WHERE is_synced = 0")
    suspend fun getUnsyncedAccounts(): List<AccountEntity>

    @Query("UPDATE accounts SET is_synced = 1 WHERE id = :accountId")
    suspend fun markAccountAsSynced(accountId: Int)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): AccountEntity?
}