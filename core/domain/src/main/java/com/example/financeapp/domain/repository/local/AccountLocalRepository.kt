package com.example.financeapp.domain.repository.local

import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.util.result.Result

interface AccountLocalRepository {
    suspend fun saveAccounts(accounts: List<AccountModel>)
    suspend fun getCachedAccounts(): Result<List<AccountModel>>
    suspend fun updateAccount(account: AccountModel, isSynced: Boolean)
    suspend fun updateAccountOffline(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel>
}