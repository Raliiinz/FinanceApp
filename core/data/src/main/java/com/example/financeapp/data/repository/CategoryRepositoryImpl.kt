package com.example.financeapp.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.domain.repository.local.CategoryLocalRepository
import com.example.financeapp.domain.repository.remote.CategoryRemoteRepository
import com.example.financeapp.network.CategoryApi
import com.example.financeapp.network.util.ApiClient
import javax.inject.Inject
import kotlin.collections.map
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Реализация репозитория для работы с данными категорий.
 *
 * Предоставляет метод для получения списка категорий с сервера.
 */
class CategoryRepositoryImpl @Inject constructor(
    private val remote: CategoryRemoteRepository,
    private val local: CategoryLocalRepository
) : CategoryRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getCategories(): Result<List<CategoryModel>> = withContext(Dispatchers.IO) {
        try {
            val remoteCategories = remote.getCategories()
            if (remoteCategories is Result.Success) {
                local.saveCategories(remoteCategories.data)
                remoteCategories
            } else {
                local.getCategories()
            }
        } catch (_: IOException) {
            local.getCategories()
        } catch (_: Exception) {
            local.getCategories()
        }
    }
}
