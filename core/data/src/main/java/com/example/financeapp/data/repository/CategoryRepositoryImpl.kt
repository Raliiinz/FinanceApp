package com.example.financeapp.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.network.CategoryApi
import com.example.financeapp.network.util.ApiClient
import javax.inject.Inject
import kotlin.collections.map
import com.example.financeapp.util.result.Result

/**
 * Реализация репозитория для работы с данными категорий.
 *
 * Предоставляет метод для получения списка категорий с сервера.
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val apiClient: ApiClient,
    private val categoryMapper: CategoryMapper,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getCategories(): Result<List<CategoryModel>> {
        return if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.getCategories() }
            when (result) {
                is Result.Success -> {
                    val now = System.currentTimeMillis()
                    val entities = result.data.map {
                        categoryMapper.toEntity(it).copy(
                            isSynced = true,
                            lastSyncedAt = now
                        )
                    }
                    categoryDao.insertAll(entities)
                    val localCategories = categoryDao.getAll()
                    Result.Success(localCategories.map { categoryMapper.entityToDomain(it) })
                }
                else -> {
                    val localCategories = categoryDao.getAll()
                    Result.Success(localCategories.map { categoryMapper.entityToDomain(it) })
                }
            }
        } else {
            val localCategories = categoryDao.getAll()
            Result.Success(localCategories.map { categoryMapper.entityToDomain(it) })
        }
    }
}
