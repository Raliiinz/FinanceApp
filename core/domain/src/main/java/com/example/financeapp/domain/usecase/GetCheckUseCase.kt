package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.repository.FinanceRepository

class GetCheckUseCase(private val financeRepository: FinanceRepository) {
    operator fun invoke(): List<Check>? = financeRepository.getChecks()
}