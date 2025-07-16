package com.example.financeapp.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.local.database.dao.TransactionDao
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.data.mapper.TransactionMapper
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.network.TransactionApi
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.FailureReason
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
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val transactionMapper: TransactionMapper,
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper
) : TransactionRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getTransactions(
        accountId: Int,
        from: String,
        to: String
    ): Result<List<TransactionModel>> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall {
                api.getTransactionsByAccountAndPeriod(accountId, from, to)
            }
            when (result) {
                is Result.Success -> {
                    val now = System.currentTimeMillis()
                    // 1. Удаляем старые транзакции за этот период и аккаунт
                    transactionDao.deleteByAccountAndPeriod(accountId, from, to)
                    // 2. Вставляем новые
                    val entities = result.data.map {
                        transactionMapper.toEntity(it).copy(
                            isSynced = true,
                            lastSyncedAt = now
                        )
                    }
                    transactionDao.insertAll(entities)
                    val local = transactionDao.getByAccountAndPeriod(accountId, from, to)
                    return Result.Success(local.mapNotNull { entity ->
                        val account = accountDao.getById(entity.accountId)?.let { accountMapper.entityToDomain(it) }
                        val category = categoryDao.getById(entity.categoryId)?.let { categoryMapper.entityToDomain(it) }
                        if (account != null && category != null)
                            transactionMapper.entityToDomain(entity, account, category)
                        else null
                    })
                }
                else -> {
                    val local = transactionDao.getByAccountAndPeriod(accountId, from, to)
                    return Result.Success(local.mapNotNull { entity ->
                        val account = accountDao.getById(entity.accountId)?.let { accountMapper.entityToDomain(it) }
                        val category = categoryDao.getById(entity.categoryId)?.let { categoryMapper.entityToDomain(it) }
                        if (account != null && category != null)
                            transactionMapper.entityToDomain(entity, account, category)
                        else null
                    })
                }
            }
        } else {
            val local = transactionDao.getByAccountAndPeriod(accountId, from, to)
            return Result.Success(local.mapNotNull { entity ->
                val account = accountDao.getById(entity.accountId)?.let { accountMapper.entityToDomain(it) }
                val category = categoryDao.getById(entity.categoryId)?.let { categoryMapper.entityToDomain(it) }
                if (account != null && category != null)
                    transactionMapper.entityToDomain(entity, account, category)
                else null
            })
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun createTransaction(transaction: TransactionModel): Result<TransactionModel> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
            val requestBody = transactionMapper.toRequest(transaction)
            val result = apiClient.safeApiCall { api.createTransaction(requestBody) }
            if (result is Result.Success) {
                val now = System.currentTimeMillis()
                val entity = transactionMapper.toEntity(result.data).copy(
                    isSynced = true,
                    lastSyncedAt = now
                )
                transactionDao.insert(entity)
                Result.Success(transactionMapper.toDomain(result.data))
            } else {
                val entity = transactionMapper.toEntity(transaction).copy(
                    isSynced = false,
                    lastSyncedAt = null
                )
                transactionDao.insert(entity)
                Result.Success(transaction)
            }
        } else {
            val entity = transactionMapper.toEntity(transaction).copy(
                isSynced = false,
                lastSyncedAt = null
            )
            transactionDao.insert(entity)
            Result.Success(transaction)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateTransaction(transaction: TransactionModel): Result<TransactionModel> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
            val requestBody = transactionMapper.toRequest(transaction)
            val result = apiClient.safeApiCall {
                api.updateTransaction(transaction.id, requestBody)
            }
            if (result is Result.Success) {
                val now = System.currentTimeMillis()
                val entity = transactionMapper.toEntity(result.data).copy(
                    isSynced = true,
                    lastSyncedAt = now
                )
                transactionDao.insert(entity)
                Result.Success(transactionMapper.toDomain(result.data))
            } else {
                val entity = transactionMapper.toEntity(transaction).copy(
                    isSynced = false,
                    lastSyncedAt = null
                )
                transactionDao.update(entity)
                Result.Success(transaction)
            }
        } else {
            val entity = transactionMapper.toEntity(transaction).copy(
                isSynced = false,
                lastSyncedAt = null
            )
            transactionDao.update(entity)
            Result.Success(transaction)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun deleteTransaction(id: Int): Result<Unit> {
        val entity = transactionDao.getById(id)
        return if (entity != null) {
            if (apiClient.networkChecker.isNetworkAvailable()) {
                val result = apiClient.safeApiCall { api.deleteTransaction(id) }
                if (result is Result.Success) {
                    transactionDao.delete(entity)
                    Result.Success(Unit)
                } else {
                    transactionDao.update(entity.copy(deleted = true, isSynced = false))
                    Result.Success(Unit)
                }
            } else {
                transactionDao.update(entity.copy(deleted = true, isSynced = false))
                Result.Success(Unit)
            }
        } else {
            Result.HttpError(FailureReason.NotFound("Transaction not found"))
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionModel> {
        val entity = transactionDao.getById(id)
        return if (entity != null) {
            val account = accountDao.getById(entity.accountId)?.let { accountMapper.entityToDomain(it) }
            val category = categoryDao.getById(entity.categoryId)?.let { categoryMapper.entityToDomain(it) }
            if (account != null && category != null)
                Result.Success(transactionMapper.entityToDomain(entity, account, category))
            else
                Result.HttpError(FailureReason.NotFound("Account or Category not found"))
        } else {
            Result.HttpError(FailureReason.NotFound("Transaction not found"))
        }
    }
}
