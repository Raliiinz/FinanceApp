package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.mapToCategory
import com.example.financeapp.data.remote.CategoryApi
import com.example.financeapp.data.remote.helper.ApiClient
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import javax.inject.Inject
import kotlin.collections.map
import com.example.financeapp.util.result.Result
import com.example.financeapp.util.result.Result.*

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val apiClient: ApiClient
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<CategoryModel>> {
        return when (val result = apiClient.safeApiCall { api.getCategories() }) {
            is Result.Success -> Success(result.data.map { it.mapToCategory() })
            is Result.HttpError -> result
            is Result.NetworkError -> result
        }
    }
}