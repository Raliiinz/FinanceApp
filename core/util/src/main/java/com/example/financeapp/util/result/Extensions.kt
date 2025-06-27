package com.example.financeapp.util.result

/**
 * Расширения для работы с [Result].
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.HttpError -> this
        is Result.NetworkError -> this
    }
}
