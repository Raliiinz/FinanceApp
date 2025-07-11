package com.example.financeapp.transaction.components

import androidx.compose.runtime.Composable
import com.example.financeapp.base.R
import com.example.financeapp.transaction.model.TransactionFormAccountUI

/**
 * Боттомшит (нижнее всплывающее окно) для выбора аккаунта из списка.
 *
 * @param accounts Список доступных аккаунтов.
 * @param onAccountSelected Колбэк при выборе аккаунта.
 * @param onDismiss Колбэк при закрытии листа.
 */

@Composable
fun AccountSelectionBottomSheet(
    accounts: List<TransactionFormAccountUI>,
    onAccountSelected: (TransactionFormAccountUI) -> Unit,
    onDismiss: () -> Unit
) {
    SelectionBottomSheet(
        titleResId = R.string.select_account,
        items = accounts,
        itemText = { "${it.name} (${it.currency})" },
        onItemSelected = onAccountSelected,
        onDismiss = onDismiss
    )
}