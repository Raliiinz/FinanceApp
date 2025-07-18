package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с анализом.
 * Ограничивает время жизни зависимостей в рамках работы с анализом расходов/доходов.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AnalysisScope