package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.util.result.Result

/**
 * репозиторий для данных об аккаунте
 */
interface AccountRepository {
    suspend fun getAccounts(): Result<List<AccountModel>>
    suspend fun updateAccounts(accounts: List<AccountModel>) : Result<Unit>
}