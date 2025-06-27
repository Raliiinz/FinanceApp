package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.TransactionMapper
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.network.TransactionApi
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.Result
import com.example.financeapp.util.result.map
import javax.inject.Inject
import kotlin.collections.map

/**
 * Реализация репозитория для работы с данными транзакций.
 *
 * Предоставляет методы для:
 * - Получения транзакций по аккаунту и периоду
 * - Создания, обновления и удаления транзакций (реализация в разработке)
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val apiClient: ApiClient,
    private val transactionMapper: TransactionMapper
) : TransactionRepository {

    override suspend fun getTransactions(
        accountId: Int,
        from: String,
        to: String
    ): Result<List<TransactionModel>> {
        return apiClient.safeApiCall {
            api.getTransactionsByAccountAndPeriod(accountId, from, to)
        }.map { transactions ->
            transactions.map(transactionMapper::toDomain)
        }
    }

    override suspend fun createTransaction(transaction: TransactionModel) {

    }

    override suspend fun updateTransaction(transaction: TransactionModel) {

    }

    override suspend fun deleteTransaction(id: Int) {

    }
}
