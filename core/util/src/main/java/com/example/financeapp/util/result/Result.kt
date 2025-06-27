package com.example.financeapp.util.result

/**
 * Обертка для результатов операций, которые могут завершиться успешно или с ошибкой.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class HttpError(val reason: FailureReason) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}