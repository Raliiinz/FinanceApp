package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.util.result.Result
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.network.AccountApi
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.map
import javax.inject.Inject
import kotlin.collections.map

/*Имплементация репозитория для данных об аккаунте*/
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val apiClient: ApiClient,
    private val accountMapper: AccountMapper,
) : AccountRepository {

    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return apiClient.safeApiCall {
            api.getAccountsList()
        }.map { accounts ->
            accounts.map(accountMapper::toDomain)
        }
    }

    override suspend fun updateAccounts(accounts: List<AccountModel>): Result<Unit> {
        return apiClient.safeApiCall {
            api.updateAccounts(
                accounts.map(accountMapper::toBriefAccount)
            )
        }
    }
}
