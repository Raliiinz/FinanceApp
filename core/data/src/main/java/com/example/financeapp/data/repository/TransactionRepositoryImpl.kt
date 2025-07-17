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
import javax.inject.Inject

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

        // Сначала пытаемся синхронизировать, если есть интернет
        if (apiClient.networkChecker.isNetworkAvailable()) {
            syncPendingTransactions()

            val result = apiClient.safeApiCall {
                api.getTransactionsByAccountAndPeriod(accountId, from, to)
            }

            if (result is Result.Success) {
                val now = System.currentTimeMillis()
                val remoteEntities = result.data.mapNotNull { response ->
                    transactionMapper.toEntity(response).copy(
                        isSynced = true,
                        lastSyncedAt = now
                    )
                }

                // Обновляем или добавляем транзакции
                remoteEntities.forEach { remoteEntity ->
                    val localEntity = remoteEntity.remoteId?.let {
                        transactionDao.getByRemoteId(it)
                    }

                    if (localEntity != null) {
                        // Обновляем существующую запись
                        val mergedEntity = remoteEntity.copy(localId = localEntity.localId)
                        transactionDao.update(mergedEntity)
                    } else {
                        // Добавляем новую запись
                        transactionDao.insert(remoteEntity)
                    }
                }
            }
        }

        // Всегда возвращаем локальные данные
        val localEntities = transactionDao.getByAccountAndPeriod(accountId, from, to)
            .filter { !it.deleted }

        val accounts = accountDao.getAll().associateBy { it.remoteId ?: it.localId }
        val categories = categoryDao.getAll().associateBy { it.id }

        return Result.Success(localEntities.mapNotNull { entity ->
            val account = accounts[entity.accountId]?.let { accountMapper.entityToDomain(it) }
            val category = categories[entity.categoryId]?.let { categoryMapper.entityToDomain(it) }
            if (account != null && category != null) {
                transactionMapper.entityToDomain(entity, account, category)
            } else {
                null
            }
        })





//        if (apiClient.networkChecker.isNetworkAvailable()) {
//            val result = apiClient.safeApiCall {
//                api.getTransactionsByAccountAndPeriod(accountId, from, to)
//            }
//            if (result is Result.Success) {
//                val now = System.currentTimeMillis()
//                val entities = result.data.mapNotNull {
//                    transactionMapper.toEntity(it).copy(
//                        isSynced = true,
//                        lastSyncedAt = now,
//                        deleted = false // Сбрасываем флаг удаления
//                    )
//                }
//
//                // Вместо удаления - обновляем существующие
//                entities.forEach { entity ->
//                    transactionDao.insert(entity) // Используем REPLACE конфликт
//                }
//            }
//        }
//
//        // Всегда возвращаем локальные данные по периоду
//        val local = transactionDao.getByAccountAndPeriod(accountId, from, to)
//            .filter { !it.deleted } // Фильтруем удалённые
//
//        val accounts = accountDao.getAll().associateBy { it.remoteId ?: it.localId }
//        val categories = categoryDao.getAll().associateBy { it.id }
//
//        return Result.Success(local.mapNotNull { entity ->
//            val account = accounts[entity.accountId]?.let { accountMapper.entityToDomain(it) }
//            val category = categories[entity.categoryId]?.let { categoryMapper.entityToDomain(it) }
//            if (account != null && category != null)
//                transactionMapper.entityToDomain(entity, account, category)
//            else null
//        })
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun createTransaction(transaction: TransactionModel): Result<TransactionModel> {

        // Сначала сохраняем локально
        val localEntity = transactionMapper.toEntity(transaction).copy(
            isSynced = !apiClient.networkChecker.isNetworkAvailable(),
            lastSyncedAt = if (apiClient.networkChecker.isNetworkAvailable())
                System.currentTimeMillis() else null
        )

        val localId = transactionDao.insert(localEntity).toInt()

        // Если есть интернет - синхронизируем
        if (apiClient.networkChecker.isNetworkAvailable()) {
            try {
                val request = transactionMapper.toRequest(transaction)
                val response = api.createTransaction(request)

                // Обновляем локальную запись с remoteId
                val updatedEntity = transactionMapper.toEntityFromCreateResponse(response, localEntity.isIncome)
                    .copy(localId = localId, isSynced = true, lastSyncedAt = System.currentTimeMillis())

                transactionDao.update(updatedEntity)

                return Result.Success(transactionMapper.entityToDomain(
                    updatedEntity,
                    accountMapper.entityToDomain(accountDao.getById(updatedEntity.accountId)!!),
                    categoryMapper.entityToDomain(categoryDao.getById(updatedEntity.categoryId)!!)
                ))
            } catch (e: Exception) {
                // Оставляем запись как несинхронизированную
                return Result.Success(transaction.copy(id = localId))
            }
        }

        return Result.Success(transaction.copy(id = localId))



//        return if (apiClient.networkChecker.isNetworkAvailable()) {
//            val requestBody = transactionMapper.toRequest(transaction)
//            val result = apiClient.safeApiCall { api.createTransaction(requestBody) }
//            if (result is Result.Success) {
//                // Сохраняем в Room только для оффлайна, но не отображаем сразу
//                val now = System.currentTimeMillis()
//                val categoryEntity = categoryDao.getById(result.data.categoryId)
//                val isIncome = categoryEntity?.isIncome ?: false
//                val entity = transactionMapper.toEntityFromCreateResponse(result.data, isIncome).copy(
//                    isSynced = true,
//                    lastSyncedAt = now
//                )
//                transactionDao.insert(entity)
//                // Возвращаем только id, UI сам обновится после getTransactions()
//                Result.Success(transaction.copy(id = result.data.id))
//            } else {
//                // Оффлайн-логика
//                val entity = transactionMapper.toEntity(transaction).copy(
//                    isSynced = false,
//                    lastSyncedAt = null
//                )
//                val localId = transactionDao.insert(entity)
//                Result.Success(transaction.copy(id = localId.toInt()))
//            }
//        } else {
//            val entity = transactionMapper.toEntity(transaction).copy(
//                isSynced = false,
//                lastSyncedAt = null
//            )
//            val localId = transactionDao.insert(entity)
//            Result.Success(transaction.copy(id = localId.toInt()))
//        }



    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateTransaction(transaction: TransactionModel): Result<TransactionModel> {
        val localEntity = transactionDao.getById(transaction.id) ?:
        return Result.HttpError(FailureReason.NotFound("Transaction not found"))

        // Обновляем локальную запись
        val updatedEntity = transactionMapper.toEntity(transaction).copy(
            localId = localEntity.localId,
            remoteId = localEntity.remoteId,
            isSynced = !apiClient.networkChecker.isNetworkAvailable(),
            lastSyncedAt = if (apiClient.networkChecker.isNetworkAvailable())
                System.currentTimeMillis() else null
        )

        transactionDao.update(updatedEntity)

        // Если есть интернет - синхронизируем
        if (apiClient.networkChecker.isNetworkAvailable()) {
            try {
                val request = transactionMapper.toRequest(transaction)

                if (updatedEntity.remoteId != null) {
                    // Обновляем существующую транзакцию на сервере
                    val response = api.updateTransaction(updatedEntity.remoteId!!, request)
                    val syncedEntity = transactionMapper.toEntity(response).copy(
                        localId = updatedEntity.localId,
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                    transactionDao.update(syncedEntity)
                    return getTransactionById(syncedEntity.localId)
                } else {
                    // Если нет remoteId, создаём новую транзакцию на сервере
                    val createResponse = api.createTransaction(request)
                    val syncedEntity = transactionMapper.toEntityFromCreateResponse(
                        createResponse,
                        updatedEntity.isIncome
                    ).copy(
                        localId = updatedEntity.localId,
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                    transactionDao.update(syncedEntity)
                    return getTransactionById(syncedEntity.localId)
                }
            } catch (e: Exception) {
                // Оставляем запись как несинхронизированную
                return getTransactionById(updatedEntity.localId)
            }
        }

        return getTransactionById(updatedEntity.localId)



//        return if (apiClient.networkChecker.isNetworkAvailable()) {
//            val requestBody = transactionMapper.toRequest(transaction)
//            val result = apiClient.safeApiCall {
//                api.updateTransaction(transaction.id, requestBody)
//            }
//            when (result) {
//                is Result.Success -> {
//                    val now = System.currentTimeMillis()
//                    // Получаем текущую локальную запись
//                    val localEntity = transactionDao.getById(transaction.id)
//
//                    // Обновляем entity с сохранением localId
//                    val entity = transactionMapper.toEntity(result.data).copy(
//                        localId = localEntity?.localId ?: 0,
//                        isSynced = true,
//                        lastSyncedAt = now
//                    )
//                    transactionDao.insert(entity)
//
//                    // Получаем обновлённую модель
//                    getTransactionById(entity.localId)
//                }
//                else -> {
//                    // Оффлайн-логика
//                    val entity = transactionMapper.toEntity(transaction).copy(
//                        isSynced = false,
//                        lastSyncedAt = null
//                    )
//                    transactionDao.update(entity)
//                    getTransactionById(transaction.id)
//                }
//            }
//        } else {
//            // Оффлайн-логика
//            val entity = transactionMapper.toEntity(transaction).copy(
//                isSynced = false,
//                lastSyncedAt = null
//            )
//            transactionDao.update(entity)
//            getTransactionById(transaction.id)
//        }



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


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private suspend fun syncPendingTransactions() {
        if (!apiClient.networkChecker.isNetworkAvailable()) return

        // Синхронизируем несинхронизированные транзакции
        val pendingTransactions = transactionDao.getUnsynced()

        pendingTransactions.forEach { entity ->
            try {
                if (entity.deleted) {
                    entity.remoteId?.let { remoteId ->
                        api.deleteTransaction(remoteId)
                        transactionDao.delete(entity)
                    } ?: run {
                        transactionDao.delete(entity)
                    }
                } else if (entity.remoteId == null) {
                    // Новая транзакция
                    val model = transactionMapper.entityToDomain(entity,
                        accountMapper.entityToDomain(accountDao.getById(entity.accountId)!!),
                        categoryMapper.entityToDomain(categoryDao.getById(entity.categoryId)!!)
                    )

                    val response = api.createTransaction(transactionMapper.toRequest(model))
                    val updatedEntity = transactionMapper.toEntityFromCreateResponse(response, entity.isIncome)
                        .copy(localId = entity.localId, isSynced = true, lastSyncedAt = System.currentTimeMillis())

                    transactionDao.update(updatedEntity)
                } else {
                    // Обновление существующей транзакции
                    val model = transactionMapper.entityToDomain(entity,
                        accountMapper.entityToDomain(accountDao.getById(entity.accountId)!!),
                        categoryMapper.entityToDomain(categoryDao.getById(entity.categoryId)!!))

                    api.updateTransaction(entity.remoteId!!, transactionMapper.toRequest(model))
                    transactionDao.update(entity.copy(isSynced = true, lastSyncedAt = System.currentTimeMillis()))
                }
            } catch (e: Exception) {
                // Оставляем запись как несинхронизированную
            }
        }
    }
}
