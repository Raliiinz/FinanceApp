package com.example.financeapp.domain.usecase.category

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.repository.CategoryRepository
import com.example.financeapp.util.result.Result
import com.example.financeapp.util.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase для получения списка категорий.
*/
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(isIncome: Boolean? = null): Result<List<CategoryModel>>  = withContext(dispatcher) { // <-- Сделать параметр nullable с дефолтом null
        categoryRepository.getCategories().map { categories ->
            if (isIncome == null) { // Если null, возвращаем все
                categories
            } else { // Иначе фильтруем
                categories.filter { it.isIncome == isIncome }
            }
        }
    }
}
