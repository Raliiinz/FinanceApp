package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.repository.FinanceRepository

class GetCategoryUseCase(private val financeRepository: FinanceRepository) {
    operator fun invoke(): List<Category>? = financeRepository.getCategories()
}