package com.example.financeapp.transaction.state

import androidx.annotation.StringRes
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.transaction.model.TransactionFormAccountUI
import com.example.financeapp.transaction.model.TransactionFormCategoryUI
import java.time.LocalDate
import java.time.LocalTime

/**
 * Состояние UI формы транзакции.
 *
 * Хранит все данные для отображения, варианты выбора, ошибки и валидаторы.
 */
data class TransactionFormUiState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val transactionId: Int? = null,

    // Данные формы
    val type: TransactionType = TransactionType.EXPENSE,
    val selectedAccount: TransactionFormAccountUI? = null,
    val selectedCategory: TransactionFormCategoryUI? = null,
    val amountInput: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val comment: String = "",

    // Дополнительные данные для выбора
    val availableAccounts: List<TransactionFormAccountUI> = emptyList(),
    val availableCategories: List<TransactionFormCategoryUI> = emptyList(),
    val showAccountSheet: Boolean = false,
    val showCategorySheet: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,

    // Состояние ошибок
    @StringRes val errorDialogMessage: Int? = null,
    @StringRes val validationError: Int? = null,
)
