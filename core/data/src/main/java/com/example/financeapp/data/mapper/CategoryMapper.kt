package com.example.financeapp.data.mapper

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.local.database.entity.CategoryEntity
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.network.pojo.response.category.CategoryResponse
import javax.inject.Inject

/**
 * Маппер для преобразования между сетевыми и доменными моделями категорий.
 */
@AppScope
class CategoryMapper @Inject constructor() {
    fun toDomain(response: CategoryResponse): CategoryModel = CategoryModel(
        id = response.id,
        iconLeading = response.emoji,
        textLeading = response.name,
        isIncome = response.isIncome
    )

    fun entityToDomain(response: CategoryEntity): CategoryModel = CategoryModel(
        id = response.id,
        iconLeading = response.iconLeading,
        textLeading = response.textLeading,
        isIncome = response.isIncome
    )

    fun toEntity(response: CategoryModel): CategoryEntity = CategoryEntity(
        id = response.id,
        iconLeading = response.iconLeading,
        textLeading = response.textLeading,
        isIncome = response.isIncome,
    )
    fun toEntity(response: CategoryResponse): CategoryEntity = CategoryEntity(
        id = response.id,
        iconLeading = response.emoji,
        textLeading = response.name,
        isIncome = response.isIncome,
    )
}
