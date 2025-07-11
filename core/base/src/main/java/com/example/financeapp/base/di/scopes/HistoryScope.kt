package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с историей операций (History).
 * Ограничивает время жизни зависимостей в рамках работы с историей.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HistoryScope