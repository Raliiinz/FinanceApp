package com.example.financeapp.domain.usecase.transaction

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase для удаления транзакции по ID
 */
class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return withContext(dispatcher) {
            transactionRepository.deleteTransaction(id)
        }
    }
}
