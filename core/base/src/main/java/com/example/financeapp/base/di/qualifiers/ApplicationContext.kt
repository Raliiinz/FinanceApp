package com.example.financeapp.base.di.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention

/**
 * Квалификатор для обозначения контекста приложения (Application Context).
 * Используется для внедрения Context, привязанного к жизненному циклу приложения.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext