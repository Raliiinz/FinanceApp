package com.example.financeapp.expenses.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.IconBox
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.domain.model.TransactionModel

/**
 * Элемент списка расходов.
 *
 * @param transaction Данные транзакции
 * @param onClick Обработчик клика
 */
@Composable
fun ExpenseListItem(
    transaction: TransactionModel,
    onClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            ExpenseLeadingContent(
                icon = transaction.category.iconLeading,
                title = transaction.category.textLeading,
                comment = transaction.comment
            )
        },
        trailingContent = {
            ExpenseTrailingContent(price = transaction.amount, currency = transaction.account.currency)
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}

@Composable
private fun ExpenseLeadingContent(
    icon: String,
    title: String,
    comment: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon = icon)
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = title,
                style = Typography.bodyLarge,
                maxLines = 1
            )
            comment?.let {
                Text(
                    text = it,
                    style = Typography.bodyMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ExpenseTrailingContent(
    price: Double,
    currency: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${price} ${currency.toCurrencySymbol()}",
            style = Typography.bodyLarge,
        )
        MoreIcon()
    }
}