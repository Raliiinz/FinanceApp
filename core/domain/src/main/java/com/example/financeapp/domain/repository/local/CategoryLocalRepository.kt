package com.example.financeapp.domain.repository.local

import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.util.result.Result

/**
 * Интерфейс репозитория для работы с категориями транзакций.
 */
interface CategoryLocalRepository {
    suspend fun getCategories(): Result<List<CategoryModel>>
    suspend fun saveCategories(categories: List<CategoryModel>)
}