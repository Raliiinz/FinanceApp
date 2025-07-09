package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.util.result.Result
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.network.AccountApi
import com.example.financeapp.network.pojo.request.AccountUpdateRequest
import com.example.financeapp.network.pojo.response.account.Account
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.map
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
) : AccountRepository {

    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return apiClient.safeApiCall {
            api.getAccountsList()
        }.map { accounts ->
            accounts.map(accountMapper::toDomain)
        }
    }

    override suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel> {
        return apiClient.safeApiCall {
            api.updateAccountById(id, AccountUpdateRequest(
                name = name,
                balance = balance,
                currency = currency))
        }.map(accountMapper::toDomain)
    }

    override suspend fun getAllCurrencies(): Result<List<String>> {
        val currencies = listOf("RUB", "USD", "EUR")
        return Result.Success(currencies)
    }
}
