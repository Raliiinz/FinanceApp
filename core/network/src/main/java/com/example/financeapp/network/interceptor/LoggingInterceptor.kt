package com.example.financeapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException

/**
 * OkHttp Interceptor для логирования сетевых запросов и ответов.
 *
 * Записывает в лог:
 * - Метод, URL, заголовки и тело запроса (если есть)
 * - Код ответа, время выполнения, заголовки и тело ответа
 * - Обрабатывает ошибки логирования, не прерывая выполнение запроса
 *
 * Использует Timber для вывода логов с тегом "NETWORK".
 */
class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestTime = System.currentTimeMillis()
        logRequest(request)
        val response = chain.proceed(request)
        return logResponse(response, requestTime)
    }

    private fun logRequest(request: Request) {
        try {
            Timber.Forest.tag("NETWORK").d("""
                |--> ${request.method} ${request.url}
                |Headers: ${request.headers}
                |Body: ${request.body?.let { bodyToString(it) }}
                |--> END ${request.method}
                """.trimMargin())
        } catch (e: Exception) {
            Timber.Forest.tag("NETWORK").e(e, "Error logging request")
        }
    }

    private fun logResponse(response: Response, requestTime: Long): Response {
        val responseTime = System.currentTimeMillis()
        val responseBody = response.body
        val responseBodyString = responseBody?.string()
        val contentType = responseBody?.contentType()

        try {
            Timber.Forest.tag("NETWORK").d("""
                |<-- ${response.code} ${response.message} ${response.request.url} (${responseTime - requestTime}ms)
                |Headers: ${response.headers}
                |Body: $responseBodyString
                |<-- END HTTP
                """.trimMargin())
        } catch (e: Exception) {
            Timber.Forest.tag("NETWORK").e(e, "Error logging response")
        }

        return response.newBuilder()
            .body(responseBodyString?.toResponseBody(contentType))
            .build()
    }

    private fun bodyToString(request: RequestBody): String {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }
}