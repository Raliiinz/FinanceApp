package com.example.financeapp.data.repository.local

import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.local.CategoryLocalRepository
import javax.inject.Inject
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryLocalRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryMapper: CategoryMapper
) : CategoryLocalRepository {

    override suspend fun saveCategories(categories: List<CategoryModel>) = withContext(Dispatchers.IO) {
        val entities = categories.map { categoryMapper.toEntity(it) }
        categoryDao.insertCategories(entities)
    }

    override suspend fun getCategories(): Result<List<CategoryModel>> = withContext(Dispatchers.IO) {
        val localCategories = categoryDao.getAllCategories()
        Result.Success(localCategories.map { categoryMapper.entityToDomain(it) })
    }
}