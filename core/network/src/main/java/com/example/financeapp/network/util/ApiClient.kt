package com.example.financeapp.network.util

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.domain.di.qualifies.IoDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import com.example.financeapp.util.result.Result

/**
 * Выполняет сетевые запросы с обработкой ошибок и повторами.
 *
 * Ответственность: Координация:
 * - Проверки сети
 * - Повторов запросов
 * - Обработки ошибок
 */
@AppScope
class ApiClient @Inject constructor(
    @IoDispatchers private val dispatcher: CoroutineDispatcher,
    private val networkChecker: NetworkChecker,
    private val errorHandler: ErrorHandler
) {
    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val RETRY_DELAY_MS = 2000L
    }

    suspend fun <T> safeApiCall(
        apiRequest: suspend () -> T
    ): Result<T> = withContext(dispatcher) {
        if (!networkChecker.isNetworkAvailable()) {
            return@withContext Result.NetworkError
        }

        var lastException: Throwable? = null

        repeat(MAX_RETRY_ATTEMPTS) { attempt ->
            try {
                return@withContext Result.Success(apiRequest())
            } catch (throwable: Throwable) {
                lastException = throwable
                if (shouldRetry(throwable, attempt)) {
                    delay(RETRY_DELAY_MS)
                } else {
                    return@withContext Result.HttpError(errorHandler.handleException(throwable))
                }
            }
        }

        Result.HttpError(errorHandler.handleException(lastException!!))
    }

    private fun shouldRetry(throwable: Throwable, attempt: Int): Boolean {
        return (throwable is HttpException &&
                throwable.code() in 500..599 &&
                attempt < MAX_RETRY_ATTEMPTS - 1)
    }
}
