package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.repository.FinanceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExpenseUseCase @Inject constructor(
    private val financeRepository: FinanceRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Expense>? {
        return withContext(dispatcher) {
            financeRepository.getExpenses()
        }
    }
}
