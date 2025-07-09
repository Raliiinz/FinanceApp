package com.example.financeapp.network

import com.example.financeapp.network.pojo.request.AccountUpdateRequest
import com.example.financeapp.network.pojo.response.account.Account
import com.example.financeapp.network.pojo.response.account.AccountBrief
import com.example.financeapp.network.pojo.response.account.AccountHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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