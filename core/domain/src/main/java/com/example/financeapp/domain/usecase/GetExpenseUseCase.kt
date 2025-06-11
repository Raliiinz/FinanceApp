package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.repository.FinanceRepository

class GetExpenseUseCase(private val financeRepository: FinanceRepository) {
    operator fun invoke(): List<Expense>? = financeRepository.getExpenses()
}