package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.network.CategoryApi
import com.example.financeapp.network.util.ApiClient
import javax.inject.Inject
import kotlin.collections.map
import com.example.financeapp.util.result.Result
import com.example.financeapp.util.result.map

/**
 * Реализация репозитория для работы с данными категорий.
 *
 * Предоставляет метод для получения списка категорий с сервера.
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val apiClient: ApiClient,
    private val categoryMapper: CategoryMapper,
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<CategoryModel>> {
        return apiClient.safeApiCall {
            api.getCategories()
        }.map { categories ->
            categories.map(categoryMapper::toDomain)
        }
    }
}
