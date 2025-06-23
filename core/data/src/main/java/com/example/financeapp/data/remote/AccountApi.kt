package com.example.financeapp.data.remote

import com.example.financeapp.data.model.account.Account
import com.example.financeapp.data.model.account.AccountHistoryResponse
import com.example.financeapp.data.model.account.UpdateAccountsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {
    @GET("accounts")
    suspend fun getAccountsList(): List<Account>

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(@Path("id") id: Int,): List<AccountHistoryResponse>

    @POST("accounts")
    suspend fun updateAccounts(@Body request: UpdateAccountsRequest): Response<Unit>
}