package com.example.financeapp.network.di

import android.content.Context
import com.example.financeapp.domain.di.qualifies.IoDispatchers
import com.example.financeapp.network.AccountApi
import com.example.financeapp.network.BuildConfig.FINANCE_API_URL
import com.example.financeapp.network.CategoryApi
import com.example.financeapp.network.TransactionApi
import com.example.financeapp.network.interceptor.AuthInterceptor
import com.example.financeapp.network.util.ApiClient
import com.example.financeapp.network.util.ErrorHandler
import com.example.financeapp.network.util.NetworkChecker
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Модуль для предоставления сетевых зависимостей.
 * Инициализирует Retrofit, API клиенты и утилиты для работы с сетью.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext context: Context
    ): NetworkChecker = NetworkChecker(context)

    @Provides
    @Singleton
    fun provideErrorHandler(
        gson: Gson
    ): ErrorHandler = ErrorHandler(gson)

    @Provides
    @Singleton
    fun provideApiClient(
        @IoDispatchers dispatcher: CoroutineDispatcher,
        networkChecker: NetworkChecker,
        errorHandler: ErrorHandler
    ): ApiClient = ApiClient(dispatcher, networkChecker, errorHandler)

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FINANCE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }
}
