package com.example.financeapp.domain.di.qualifies

import jakarta.inject.Qualifier

/**
 * Квалификатор для внедрения [CoroutineDispatcher], предназначенного для ввода-вывода (IO) операций.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatchers
