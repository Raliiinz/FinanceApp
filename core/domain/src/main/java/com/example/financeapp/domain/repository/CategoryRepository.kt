package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.util.result.Result

/**
 * Интерфейс репозитория для работы с категориями транзакций.
 */
interface CategoryRepository {
    suspend fun getCategories(): Result<List<CategoryModel>>
}