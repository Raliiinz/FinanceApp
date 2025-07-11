package com.example.financeapp.transaction.components

import androidx.compose.runtime.Composable
import com.example.financeapp.base.R
import com.example.financeapp.transaction.model.TransactionFormCategoryUI

/**
 * Боттомшит для выбора категории транзакции.
 *
 * @param categories Список доступных категорий.
 * @param onCategorySelected Колбэк при выборе категории.
 * @param onDismiss Колбэк при закрытии окна.
 */
@Composable
fun CategorySelectionBottomSheet(
    categories: List<TransactionFormCategoryUI>,
    onCategorySelected: (TransactionFormCategoryUI) -> Unit,
    onDismiss: () -> Unit
) {
    SelectionBottomSheet(
        titleResId = R.string.select_category,
        items = categories,
        itemText = { "${it.emoji} ${it.name}" },
        onItemSelected = onCategorySelected,
        onDismiss = onDismiss
    )
}