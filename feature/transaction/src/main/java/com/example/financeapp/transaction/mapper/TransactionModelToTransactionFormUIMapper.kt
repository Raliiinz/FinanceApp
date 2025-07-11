package com.example.financeapp.transaction.mapper

import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.transaction.state.TransactionFormUiState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

/**
 * Маппер для преобразования полной [TransactionModel] в UI-состояние формы — [TransactionFormUiState].
 *
 * Используется при редактировании уже существующей транзакции.
 */
class TransactionModelToTransactionFormUIMapper @Inject constructor(
    private val accountMapper: AccountModelToTransactionFormUIMapper,
    private val categoryMapper: CategoryModelToTransactionFormUIMapper
 ) {
    fun map(domain: TransactionModel) = TransactionFormUiState(
        isEditing = true,
        transactionId = domain.id,
        type = if (domain.category.isIncome) TransactionType.INCOME else TransactionType.EXPENSE,
        selectedAccount = accountMapper.map(domain.account),
        selectedCategory = categoryMapper.map(domain.category),
        amountInput = domain.amount.toString(),
        date = LocalDate.parse(domain.date.substringBefore("T")), // Или ваш формат
        time = LocalTime.parse(domain.date.substringAfter("T")), // Или ваш формат
        comment = domain.comment ?: ""
    )
 }