package com.example.financeapp.data.repository.remote

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.remote.CategoryRemoteRepository
import com.example.financeapp.network.CategoryApi
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRemoteRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val apiClient: ApiClient,
    private val categoryMapper: CategoryMapper
) : CategoryRemoteRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getCategories(): Result<List<CategoryModel>>  {
        return (if (apiClient.networkChecker.isNetworkAvailable()) {
            val result = apiClient.safeApiCall { api.getCategories() }
            when (result) {
                is Result.Success -> {
                    val models = result.data.map { categoryMapper.toEntity(it) }
                    Result.Success(models.map { categoryMapper.entityToDomain(it) })
                }

                else -> result
            }
        } else {
            Result.NetworkError
        }) as Result<List<CategoryModel>>
    }
}