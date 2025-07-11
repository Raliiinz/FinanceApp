package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с аккаунтом (Account).
 * Ограничивает время жизни зависимостей в рамках работы с аккаунтом.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AccountScope