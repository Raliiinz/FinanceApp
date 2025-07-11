package com.example.financeapp.network.util

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.util.result.FailureReason
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Обрабатывает ошибки сетевых запросов.
 *
 * Ответственность:
 * - Преобразование исключений в FailureReason
 * - Парсинг ошибок сервера
 */
@AppScope
class ErrorHandler @Inject constructor(
    private val gson: Gson
) {
    fun handleException(throwable: Throwable): FailureReason {
        return when (throwable) {
            is IOException -> FailureReason.NetworkError(throwable.message)
            is HttpException -> throwable.toFailureReason()
            else -> FailureReason.Unknown(throwable.message)
        }
    }

    fun HttpException.toFailureReason(): FailureReason {
        return when (code()) {
            400 -> FailureReason.BadRequest(getErrorMessage())
            401 -> FailureReason.Unauthorized(getErrorMessage())
            403 -> FailureReason.Forbidden(getErrorMessage())
            404 -> FailureReason.NotFound(getErrorMessage())
            409 -> FailureReason.Conflict(getErrorMessage())
            in 500..599 -> FailureReason.ServerError(getErrorMessage())
            else -> FailureReason.Unknown(getErrorMessage())
        }
    }

    private fun HttpException.getErrorMessage(): String? {
        return try {
            response()?.errorBody()?.string()?.takeIf { it.isNotEmpty() }
                ?.let { gson.fromJson(it, ErrorResponse::class.java)?.error }
                ?: message()
        } catch (_: Exception) {
            message()
        }
    }
}