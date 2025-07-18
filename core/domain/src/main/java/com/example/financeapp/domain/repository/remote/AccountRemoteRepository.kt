package com.example.financeapp.domain.repository.remote

import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.util.result.Result

interface AccountRemoteRepository {
    suspend fun getAccounts(): Result<List<AccountModel>>
    suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ) : Result<AccountModel>
}