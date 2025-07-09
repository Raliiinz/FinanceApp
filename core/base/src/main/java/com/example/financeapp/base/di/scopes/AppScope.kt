package com.example.financeapp.base.di.scopes

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope // Для объектов, живущих дольше, чем Application