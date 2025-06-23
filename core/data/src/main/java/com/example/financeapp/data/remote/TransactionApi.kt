package com.example.financeapp.data.remote

import com.example.financeapp.data.model.transaction.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountAndPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") from: String,
        @Query("endDate") to: String
    ): List<TransactionResponse>
}