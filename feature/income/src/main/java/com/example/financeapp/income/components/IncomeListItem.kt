package com.example.financeapp.income.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.domain.model.TransactionModel

/**
 * Элемент списка доходов.
 *
 * @param transaction Данные транзакции
 * @param onClick Обработчик клика
 */
@Composable
fun IncomeListItem(
    transaction: TransactionModel,
    onClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            Text(
                text = transaction.category.textLeading,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingContent = {
            IncomeTrailingContent(amount = transaction.amount, currency = transaction.account.currency)
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 72.dp
    )
}

@Composable
private fun IncomeTrailingContent(
    amount: Double,
    currency: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${amount.toDouble()} ${currency.toCurrencySymbol()}",
            style = Typography.bodyLarge,
        )
        MoreIcon()
    }
}