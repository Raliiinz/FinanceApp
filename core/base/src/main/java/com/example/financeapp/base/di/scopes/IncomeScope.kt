package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с доходами (Income).
 * Обеспечивает правильное время жизни зависимостей в модуле доходов.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class IncomeScope