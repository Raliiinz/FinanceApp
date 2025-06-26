package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.util.result.Result

/**
 * репозиторий для данных о категориях
 */
interface CategoryRepository {
    suspend fun getCategories(): Result<List<CategoryModel>>
}