package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.util.result.Result

/**
 * Интерфейс репозитория для работы с данными аккаунтов.
 */
interface AccountRepository {
    suspend fun getAccounts(): Result<List<AccountModel>>
    suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ) : Result<AccountModel>
    suspend fun getAllCurrencies() : Result<List<String>>
}