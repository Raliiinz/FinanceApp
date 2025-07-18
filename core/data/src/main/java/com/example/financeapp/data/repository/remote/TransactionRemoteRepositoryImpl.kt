package com.example.financeapp.data.repository.remote

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.mapper.TransactionMapper
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.remote.TransactionRemoteRepository
import com.example.financeapp.network.TransactionApi
import com.example.financeapp.network.util.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.financeapp.util.result.Result

class TransactionRemoteRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val apiClient: ApiClient,
    private val transactionMapper: TransactionMapper
) : TransactionRemoteRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getTransactions(
        accountId: Int,
        from: String,
        to: String
    ): Result<List<TransactionModel>> = withContext(Dispatchers.IO) {
        if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall {
                api.getTransactionsByAccountAndPeriod(accountId, from, to)
            }
            when (result) {
                is Result.Success -> {
                    val models = result.data.map { transactionMapper.toDomain(it) }
                    Result.Success(models)
                }
                else -> result as Result<List<TransactionModel>>
            }
        } else {
            Result.NetworkError
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun createTransaction(transaction: TransactionModel): Result<TransactionModel> = withContext(Dispatchers.IO) {
        if (apiClient.networkChecker.isNetworkAvailable()) {
            val request = transactionMapper.toRequest(transaction)
            val result = apiClient.safeApiCall { api.createTransaction(request) }
            when (result) {
                is Result.Success -> Result.Success(transaction.copy(id = result.data.id))
                else -> result as Result<TransactionModel>
            }
        } else {
            Result.NetworkError
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getTransactionById(id: Int): Result<TransactionModel> = withContext(Dispatchers.IO) {
        if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.getTransactionById(id) }
            when (result) {
                is Result.Success -> Result.Success(transactionMapper.toDomain(result.data))
                else -> result as Result<TransactionModel>
            }
        } else {
            Result.NetworkError
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateTransaction(transaction: TransactionModel): Result<TransactionModel> = withContext(Dispatchers.IO) {
        if (apiClient.networkChecker.isNetworkAvailable()) {
            val request = transactionMapper.toRequest(transaction)
            val result = apiClient.safeApiCall { api.updateTransaction(transaction.id, request) }
            when (result) {
                is Result.Success -> Result.Success(transactionMapper.toDomain(result.data))
                else -> result as Result<TransactionModel>
            }
        } else {
            Result.NetworkError
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun deleteTransaction(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.deleteTransaction(id) }
            when (result) {
                is Result.Success -> Result.Success(Unit)
                else -> result as Result<Unit>
            }
        } else {
            Result.Success(Unit)
        }
    }
}
