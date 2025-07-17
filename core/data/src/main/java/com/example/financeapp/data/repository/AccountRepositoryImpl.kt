package com.example.financeapp.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.util.result.Result
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.network.AccountApi
import com.example.financeapp.network.pojo.request.account.AccountUpdateRequest
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.map
import java.util.Date
import javax.inject.Inject
import kotlin.collections.map

/**
 * Реализация репозитория для работы с данными аккаунтов.
 *
 * Предоставляет методы для:
 * - Получения списка аккаунтов с сервера
 * - Обновления информации об аккаунтах на сервере
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val apiClient: ApiClient,
    private val accountMapper: AccountMapper,
    private val accountDao: AccountDao
) : AccountRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.getAccountsList() }
            when (result) {
                is Result.Success -> {
                    val entities = result.data.map {
                        accountMapper.toEntity(it).copy(
                            isSynced = true,
                            lastSyncedAt = Date().time
                        )
                    }
                    accountDao.insertAll(entities)

                    val localAccounts = accountDao.getAll()
                    Result.Success(localAccounts.map { accountMapper.entityToDomain(it) })
                }
                else -> {
                    val localAccounts = accountDao.getAll()
                    Result.Success(localAccounts.map { accountMapper.entityToDomain(it) })
                }
            }
        } else {
            val localAccounts = accountDao.getAll()
            Result.Success(localAccounts.map { accountMapper.entityToDomain(it) })
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
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
                    val now = Date().time
                    // Получаем текущую запись из БД
                    val local = accountDao.getById(id)

                    // Создаём новую сущность с сохранением localId
                    val entity = accountMapper.toEntity(result.data).copy(
                        localId = local?.localId ?: 0,
                        isSynced = true,
                        lastSyncedAt = now
                    )

                    accountDao.insert(entity)

                    // Получаем обновлённые данные из БД
                    val updated = accountDao.getById(entity.localId) ?: accountDao.getById(id)
                    updated?.let {
                        Result.Success(accountMapper.entityToDomain(it))
                    } ?: Result.HttpError(FailureReason.NotFound("Account not found after update"))
                }
                else -> {
                    // Оффлайн-логика
                    val local = accountDao.getById(id)
                    local?.let {
                        val unsynced = it.copy(
                            name = name,
                            balance = balance,
                            currency = currency,
                            isSynced = false
                        )
                        accountDao.update(unsynced)
                        Result.Success(accountMapper.entityToDomain(unsynced))
                    } ?: Result.HttpError(FailureReason.NotFound("Account not found in local database"))
                }
            }
        } else {
            // Оффлайн-логика
            val local = accountDao.getById(id)
            local?.let {
                val unsynced = it.copy(
                    name = name,
                    balance = balance,
                    currency = currency,
                    isSynced = false
                )
                accountDao.update(unsynced)
                Result.Success(accountMapper.entityToDomain(unsynced))
            } ?: Result.HttpError(FailureReason.NotFound("Account not found in local database"))
        }
//            if (result is Result.Success) {
//                val now = Date().time
//                val entity = accountMapper.toEntity(result.data).copy(
//                    isSynced = true,
//                    lastSyncedAt = now
//                )
//                accountDao.insert(entity)
//                Result.Success(accountMapper.toDomain(result.data))
//            } else {
//                val local = accountDao.getById(id)
//                local?.let {
//                    val unsynced = it.copy(
//                        name = name,
//                        balance = balance,
//                        currency = currency,
//                        isSynced = false
//                    )
//                    accountDao.update(unsynced)
//                    Result.Success(accountMapper.entityToDomain(unsynced))
//                } ?: Result.HttpError(FailureReason.NotFound("Account not found in local database"))
//            }
//        } else {
//            val local = accountDao.getById(id)
//            local?.let {
//                val unsynced = it.copy(
//                    name = name,
//                    balance = balance,
//                    currency = currency,
//                    isSynced = false
//                )
//                accountDao.update(unsynced)
//                Result.Success(accountMapper.entityToDomain(unsynced))
//            } ?: Result.HttpError(FailureReason.NotFound("Account not found in local database"))

    }

    override suspend fun getAllCurrencies(): Result<List<String>> {
        val currencies = listOf("RUB", "USD", "EUR")
        return Result.Success(currencies)
    }
}
