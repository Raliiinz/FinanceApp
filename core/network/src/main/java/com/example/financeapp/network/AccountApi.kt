package com.example.financeapp.network

import com.example.financeapp.network.pojo.response.account.Account
import com.example.financeapp.network.pojo.response.account.AccountBrief
import com.example.financeapp.network.pojo.response.account.AccountHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit интерфейс для работы с API аккаунтов.
 */
interface AccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<Account>

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(@Path("id") id: Int,): List<AccountHistoryResponse>

    @POST("accounts")
    suspend fun updateAccounts(@Body request: List<AccountBrief>): Response<Unit>
}