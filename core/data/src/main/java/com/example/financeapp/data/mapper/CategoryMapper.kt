package com.example.financeapp.data.mapper

import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.network.pojo.response.category.CategoryResponse
import javax.inject.Inject
import javax.inject.Singleton


/*Маппер для работы с модельками категорий*/
@Singleton
class CategoryMapper @Inject constructor() {
    fun toDomain(response: CategoryResponse): CategoryModel = CategoryModel(
        id = response.id,
        iconLeading = response.emoji,
        textLeading = response.name,
        isIncome = response.isIncome
    )
}
