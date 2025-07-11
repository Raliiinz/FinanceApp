package com.example.financeapp.network

import com.example.financeapp.network.pojo.request.account.AccountUpdateRequest
import com.example.financeapp.network.pojo.response.account.Account
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit интерфейс для работы с API аккаунтов.
 */
interface AccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<Account>

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") accountId: Int,
        @Body updateAccountRequest: AccountUpdateRequest
    ): Account
}