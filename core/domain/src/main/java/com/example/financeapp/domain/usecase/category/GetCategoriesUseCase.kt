package com.example.financeapp.domain.usecase.category

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<CategoryModel>> {
        return withContext(dispatcher) {
            categoryRepository.getCategories()
        }
    }
}
