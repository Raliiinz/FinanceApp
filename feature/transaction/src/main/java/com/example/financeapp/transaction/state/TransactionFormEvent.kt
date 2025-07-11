package com.example.financeapp.transaction.state

import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.transaction.model.TransactionFormAccountUI
import com.example.financeapp.transaction.model.TransactionFormCategoryUI
import java.time.LocalDate
import java.time.LocalTime

/**
 * События формы транзакции, отправляемые из UI в ViewModel.
 */
sealed class TransactionFormEvent {
    data class InitForm(val transactionId: Int? = null, val transactionType: TransactionType? = null) : TransactionFormEvent()
    data class UpdateAmount(val amount: String) : TransactionFormEvent()
    data class UpdateComment(val comment: String) : TransactionFormEvent()
    data class SelectAccount(val account: TransactionFormAccountUI) : TransactionFormEvent()
    data class SelectCategory(val category: TransactionFormCategoryUI) : TransactionFormEvent()
    data class UpdateDate(val date: LocalDate) : TransactionFormEvent()
    data class UpdateTime(val time: LocalTime) : TransactionFormEvent()
    object ShowAccountSheet : TransactionFormEvent()
    object HideAccountSheet : TransactionFormEvent()
    object ShowCategorySheet : TransactionFormEvent()
    object HideCategorySheet : TransactionFormEvent()
    object ShowDatePicker : TransactionFormEvent()
    object HideDatePicker : TransactionFormEvent()
    object ShowTimePicker : TransactionFormEvent()
    object HideTimePicker : TransactionFormEvent()
    object SaveTransaction : TransactionFormEvent()
    object DeleteTransaction : TransactionFormEvent() // Только для редактирования
    object HideErrorDialog : TransactionFormEvent()
}