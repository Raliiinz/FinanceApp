package com.example.financeapp.domain.usecase.account

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<String>> {
        return withContext(dispatcher) {
            accountRepository.getAllCurrencies()
        }
    }
}