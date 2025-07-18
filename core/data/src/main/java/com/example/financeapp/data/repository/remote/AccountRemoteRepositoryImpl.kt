package com.example.financeapp.data.repository.remote

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.remote.AccountRemoteRepository
import com.example.financeapp.network.AccountApi
import com.example.financeapp.network.pojo.request.account.AccountUpdateRequest
import com.example.financeapp.network.util.ApiClient
import javax.inject.Inject
import com.example.financeapp.util.result.Result

class AccountRemoteRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val apiClient: ApiClient,
    private val accountMapper: AccountMapper
) : AccountRemoteRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return (if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.getAccountsList() }
            when (result) {
                is Result.Success -> {
                    val models = result.data.map { accountMapper.toEntity(it) }
                    Result.Success(models.map { accountMapper.entityToDomain(it) })
                }

                else -> result
            }
        } else {
            Result.NetworkError
        }) as Result<List<AccountModel>>
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel> {
        return (if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall {
                api.updateAccountById(
                    id,
                    AccountUpdateRequest(
                        name = name,
                        balance = balance,
                        currency = currency
                    )
                )
            }
            when (result) {
                is Result.Success -> {
                    Result.Success(accountMapper.entityToDomain(accountMapper.toEntity(result.data)))
                }

                else -> result
            }
        } else {
            Result.NetworkError
        }) as Result<AccountModel>
    }
}
