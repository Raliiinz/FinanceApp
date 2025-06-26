package com.example.financeapp.network

import com.example.financeapp.network.pojo.response.transaction.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit интерфейс для работы с API транзакций.
 */
interface TransactionApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountAndPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") from: String,
        @Query("endDate") to: String
    ): List<TransactionResponse>
}