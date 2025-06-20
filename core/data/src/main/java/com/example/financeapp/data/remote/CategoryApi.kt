package com.example.financeapp.data.remote

import com.example.financeapp.data.model.category.CategoryResponse
import retrofit2.http.GET

interface CategoryApi {
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>
}