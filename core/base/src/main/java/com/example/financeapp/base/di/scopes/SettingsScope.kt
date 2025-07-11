package com.example.financeapp.base.di.scopes

import javax.inject.Scope

/**
 * Dagger-скоуп для зависимостей, связанных с настройками (Settings).
 * Ограничивает время жизни зависимостей в рамках работы с настройками.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsScope