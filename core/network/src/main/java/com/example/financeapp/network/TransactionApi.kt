package com.example.financeapp.network

import com.example.financeapp.network.pojo.request.transaction.TransactionRequest
import com.example.financeapp.network.pojo.response.transaction.TransactionCreateResponse
import com.example.financeapp.network.pojo.response.transaction.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int
    ): TransactionResponse

    @POST("transactions")
    suspend fun createTransaction(
        @Body request: TransactionRequest
    ): TransactionCreateResponse
//    @POST("transactions")
//    suspend fun createTransaction(
//        @Body request: TransactionRequest
//    ): TransactionResponse

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequest
    ): TransactionResponse

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") id: Int
    )
}