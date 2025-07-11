package com.example.financeapp.transaction.model

/**
 * UI-модель категории, используемая для отображения и выбора категории транзакции.
 *
 * @param id Уникальный идентификатор категории.
 * @param name Имя/название категории.
 * @param emoji Иконка или эмодзи, представляющая категорию.
 * @param income Флаг, обозначающий, является ли категория доходной (true) или расходной (false).
 */
data class TransactionFormCategoryUI(
    val id: Int,
    val name: String,
    val emoji: String,
    val income: Boolean,
)