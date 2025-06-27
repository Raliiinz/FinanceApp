package com.example.financeapp.util.result

/**
 * Класс для представления причин ошибок в приложении
 */
sealed class FailureReason {
    data class BadRequest(val message: String? = null) : FailureReason()
    data class Unauthorized(val message: String? = null) : FailureReason()
    data class Forbidden(val message: String? = null) : FailureReason()
    data class NotFound(val message: String? = null) : FailureReason()
    data class Conflict(val message: String? = null) : FailureReason()
    data class ServerError(val message: String? = null) : FailureReason()
    data class Unknown(val message: String? = null) : FailureReason()
    data class NetworkError(val message: String? = null) : FailureReason()
}