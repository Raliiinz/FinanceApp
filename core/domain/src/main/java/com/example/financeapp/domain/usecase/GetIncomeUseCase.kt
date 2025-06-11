package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.model.Income
import com.example.financeapp.domain.repository.FinanceRepository

class GetIncomeUseCase(private val financeRepository: FinanceRepository) {
    operator fun invoke(): List<Income>? = financeRepository.getIncomes()
}