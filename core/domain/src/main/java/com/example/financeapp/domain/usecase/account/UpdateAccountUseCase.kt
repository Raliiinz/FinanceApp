package com.example.financeapp.domain.usecase.account

import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.AccountRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase для обновления данных аккаунтов.
 */
class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ): Result<AccountModel> {
        return withContext(dispatcher) {
            accountRepository.updateAccount(
                id = id,
                name = name,
                balance = balance,
                currency = currency
            )
        }
    }
}
