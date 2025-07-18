package com.example.financeapp.data.repository

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import javax.inject.Inject
import com.example.financeapp.domain.repository.local.TransactionLocalRepository
import com.example.financeapp.domain.repository.remote.TransactionRemoteRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Реализация репозитория для работы с данными транзакций.
 *
 * Предоставляет методы для:
 * - Получения транзакций по аккаунту и периоду
 * - Создания, обновления и удаления транзакций (реализация в разработке)
 */

class TransactionRepositoryImpl @Inject constructor(
    private val remote: TransactionRemoteRepository,
    private val local: TransactionLocalRepository
) : TransactionRepository {

    override suspend fun getTransactions(
        accountId: Int,
        from: String,
        to: String
    ): Result<List<TransactionModel>> {
        return try {
            val remoteTransactions = remote.getTransactions(accountId, from, to)
            if (remoteTransactions is Result.Success) {
                local.saveTransactions(remoteTransactions.data)
                remoteTransactions
            } else {
                Result.Success(local.getCachedTransactions(accountId, from, to))
            }
        } catch (_: IOException) {
            Result.Success(local.getCachedTransactions(accountId, from, to))
        } catch (_: Exception) {
            Result.Success(local.getCachedTransactions(accountId, from, to))
        }
    }

    override suspend fun createTransaction(transaction: TransactionModel): Result<TransactionModel> {
        return try {
            val result = remote.createTransaction(transaction)
            if (result is Result.Success) {
                local.addTransaction(result.data, true)
                result
            } else {
                local.addTransaction(transaction, false)
                Result.Success(transaction)
            }
        } catch (_: IOException) {
            local.addTransaction(transaction, false)
            Result.Success(transaction)
        } catch (_: Exception) {
            local.addTransaction(transaction, false)
            Result.Success(transaction)
        }
    }

    override suspend fun updateTransaction(transaction: TransactionModel): Result<TransactionModel> = withContext(Dispatchers.IO) {
        try {
            val result = remote.updateTransaction(transaction)
            if (result is Result.Success) {
                local.updateTransaction(result.data)
                result
            } else {
                local.addTransaction(transaction, false)
                Result.Success(transaction)
            }
        } catch (_: IOException) {
            local.addTransaction(transaction, false)
            Result.Success(transaction)
        } catch (_: Exception) {
            local.addTransaction(transaction, false)
            Result.Success(transaction)
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionModel> = withContext(Dispatchers.IO) {
        try {
            val result = remote.getTransactionById(id)
            if (result is Result.Success) {
                local.addTransaction(result.data, true)
                Result.Success(result.data)
            } else {
                Result.Success(local.getTransactionById(id))
            }
        } catch (_: IOException) {
            Result.Success(local.getTransactionById(id))
        } catch (_: Exception) {
            Result.Success(local.getTransactionById(id))
        }
    }


    override suspend fun deleteTransaction(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val result = remote.deleteTransaction(id)
            local.deleteTransaction(id)
            result
        } catch (_: IOException) {
            local.deleteTransaction(id)
            Result.Success(Unit)
        } catch (_: Exception) {
            local.deleteTransaction(id)
            Result.Success(Unit)
        }
    }

    override suspend fun getUnsyncedTransactions(): Result<List<TransactionModel>> = withContext(Dispatchers.IO) {
        local.getUnsyncedTransactions() as Result<List<TransactionModel>>
    }

    override suspend fun markAsSynced(id: Int) = withContext(Dispatchers.IO) {
        local.markAsSynced(id)
    }
}