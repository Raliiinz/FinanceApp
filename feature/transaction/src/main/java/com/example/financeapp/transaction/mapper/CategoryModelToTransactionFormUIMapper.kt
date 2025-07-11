package com.example.financeapp.transaction.mapper

import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.transaction.model.TransactionFormCategoryUI
import javax.inject.Inject

/**
 * Маппер для преобразования [CategoryModel] в [TransactionFormCategoryUI],
 * используемую в UI-экране транзакций.
 */
class CategoryModelToTransactionFormUIMapper @Inject constructor() {
    fun map(domain: CategoryModel) = TransactionFormCategoryUI(
        domain.id,
        domain.textLeading,
        domain.iconLeading,
        domain.isIncome
    )
    fun mapList(domains: List<CategoryModel>) = domains.map { map(it) }
 }