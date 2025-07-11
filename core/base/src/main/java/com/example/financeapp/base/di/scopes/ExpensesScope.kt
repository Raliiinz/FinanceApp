package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с расходами (Expenses).
 * Обеспечивает правильное время жизни зависимостей в модуле расходов.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ExpensesScope