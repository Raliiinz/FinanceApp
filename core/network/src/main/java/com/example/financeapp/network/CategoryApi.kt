package com.example.financeapp.network

import com.example.financeapp.network.pojo.response.category.CategoryResponse
import retrofit2.http.GET

/**
 * Retrofit интерфейс для работы с API категорий.
 */
interface CategoryApi {
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>
}