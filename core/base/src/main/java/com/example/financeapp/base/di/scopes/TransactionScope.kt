package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с транзакциями (Transaction).
 * Обеспечивает правильное время жизни зависимостей в модуле транзакций.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionScope