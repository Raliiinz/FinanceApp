package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.toAccountBrief
import com.example.financeapp.data.mapper.toDomain
import com.example.financeapp.data.model.account.UpdateAccountsRequest
import com.example.financeapp.data.remote.AccountApi
import com.example.financeapp.data.remote.helper.ApiClient
import com.example.financeapp.util.Result
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.util.Result.*
import javax.inject.Inject
import kotlin.collections.map

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val apiClient: ApiClient
) : AccountRepository {

    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return when (val result = apiClient.safeApiCall { api.getAccountsList() }) {
            is Result.Success -> Success(result.data.map { it.toDomain() })
            is Result.HttpError -> result
            is Result.NetworkError -> result
        }
    }

    override suspend fun updateAccounts(accounts: List<AccountModel>): Result<Unit> {
        val request = UpdateAccountsRequest(
            accounts = accounts.map { it.toAccountBrief() }
        )
        return apiClient.safeApiCall { api.updateAccounts(request) }
    }

//    override suspend fun getAccountHistoryById(id: Int): Result<List<AccountHistoryResponse>> {
//        return apiClient.safeApiCall { api.getAccountHistoryById(id) }
//    }
}
