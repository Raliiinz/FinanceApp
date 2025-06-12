package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.repository.FinanceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCheckUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Check>? {
        return withContext(dispatcher) {
            financeRepository.getChecks()
        }
    }
}