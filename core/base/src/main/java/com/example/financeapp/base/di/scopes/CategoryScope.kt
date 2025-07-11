package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с категориями (Category).
 * Ограничивает время жизни зависимостей в рамках работы с категориями.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CategoryScope