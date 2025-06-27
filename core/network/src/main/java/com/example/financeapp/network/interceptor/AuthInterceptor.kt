package com.example.financeapp.network.interceptor

import com.example.financeapp.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Перехватчик для добавления JWT токена в заголовки запросов.
 * Автоматически добавляет Authorization header ко всем сетевым запросам.
 */
class AuthInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.financeApiKey}")
            .build()

        return chain.proceed(request)
    }
}