package com.example.financeapp.domain.usecase.transaction

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int) {
        return withContext(dispatcher) {
            transactionRepository.deleteTransaction(id)
        }
    }
}