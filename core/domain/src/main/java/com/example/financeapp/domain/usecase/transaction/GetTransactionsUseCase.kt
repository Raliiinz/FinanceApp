package com.example.financeapp.domain.usecase.transaction

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase для получения транзакций за период.
 */
class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(accountId: Int, from: String, to: String): Result<List<TransactionModel>> {
        return withContext(dispatcher) {
            transactionRepository.getTransactions(accountId, from, to)
        }
    }
}
