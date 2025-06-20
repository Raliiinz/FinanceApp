package com.example.financeapp.data.remote.interceptor

import com.example.financeapp.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.financeApiKey}")
            .build()

        return chain.proceed(request)
    }
}