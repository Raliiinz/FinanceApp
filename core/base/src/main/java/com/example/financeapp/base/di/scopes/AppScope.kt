package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей уровня всего приложения (Application-scoped).
 * Зависимости с этим скоупом живут, пока работает приложение.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope