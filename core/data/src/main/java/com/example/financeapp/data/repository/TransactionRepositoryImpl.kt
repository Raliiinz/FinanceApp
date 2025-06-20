package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.toDomain
import com.example.financeapp.data.remote.TransactionApi
import com.example.financeapp.data.remote.helper.ApiClient
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.util.result.Result
import com.example.financeapp.util.result.Result.*
import javax.inject.Inject
import kotlin.collections.map

class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val apiClient: ApiClient
) : TransactionRepository {

    override suspend fun getTransactions(
        accountId: Int,
        from: String,
        to: String
    ): Result<List<TransactionModel>> {
        return when (val result = apiClient.safeApiCall {
            api.getTransactionsByAccountAndPeriod(accountId, from, to)
        }) {
            is Result.Success -> Success(result.data.map { it.toDomain() })
            is Result.HttpError -> result
            is Result.NetworkError -> result
        }
    }

    override suspend fun createTransaction(transaction: TransactionModel) {
//        val request = transaction.toCreateRequest()
//        api.createTransaction(request)
    }

    override suspend fun updateTransaction(transaction: TransactionModel) {
//        val request = transaction.toUpdateRequest()
//        api.updateTransaction(request)
    }

    override suspend fun deleteTransaction(id: Int) {
        //       api.deleteTransaction(DeleteTransactionRequest(id.toString()))
    }
}