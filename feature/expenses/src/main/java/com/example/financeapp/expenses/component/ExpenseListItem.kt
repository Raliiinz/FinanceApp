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
import com.example.financeapp.base.ui.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
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
                icon = transaction.categoryEmoji,
                title = transaction.categoryName,
                comment = transaction.comment
            )
        },
        trailingContent = {
            ExpenseTrailingContent(price = transaction.amount)
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatPrice(price),
            style = Typography.bodyLarge,
        )
        MoreIcon()
    }
}