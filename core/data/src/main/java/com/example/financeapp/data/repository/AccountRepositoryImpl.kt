package com.example.financeapp.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.util.result.Result
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.domain.repository.local.AccountLocalRepository
import com.example.financeapp.domain.repository.remote.AccountRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Реализация репозитория для работы с данными аккаунтов.
 *
 * Предоставляет методы для:
 * - Получения списка аккаунтов с сервера
 * - Обновления информации об аккаунтах на сервере
 */
class AccountRepositoryImpl @Inject constructor(
    private val remote: AccountRemoteRepository,
    private val local: AccountLocalRepository
) : AccountRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getAccounts(): Result<List<AccountModel>> = withContext(Dispatchers.IO) {
        try {
            val remoteAccounts = remote.getAccounts()
            if (remoteAccounts is Result.Success) {
                local.saveAccounts(remoteAccounts.data)
                remoteAccounts
            } else {
                local.getCachedAccounts()
            }
        } catch (_: IOException) {
            local.getCachedAccounts()
        } catch (_: Exception) {
            local.getCachedAccounts()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel> = withContext(Dispatchers.IO) {
        try {
            val result = remote.updateAccount(id, name, balance, currency)
            if (result is Result.Success) {
                local.updateAccount(result.data, true)
                result
            } else {
                local.updateAccountOffline(id, name, balance, currency)
            }
        } catch (_: IOException) {
            local.updateAccountOffline(id, name, balance, currency)
        } catch (_: Exception) {
            local.updateAccountOffline(id, name, balance, currency)
        }
    }

    override suspend fun getAllCurrencies(): Result<List<String>> {
        val currencies = listOf("RUB", "USD", "EUR")
        return Result.Success(currencies)
    }
}