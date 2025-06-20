package com.example.financeapp.domain.usecase.transaction

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(transaction: TransactionModel) {
        return withContext(dispatcher) {
            transactionRepository.createTransaction(transaction)
        }
    }

}
