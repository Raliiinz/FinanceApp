package com.example.financeapp.expenses.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol

/**
 * Компонент отображения общей суммы расходов.
 *
 * @param totalPrice Общая сумма для отображения
 */
@Composable
fun TotalPriceItem(
    totalPrice: Double,
    currency: String,
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(R.string.total),
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Text(
                text = "${totalPrice.toDouble()} ${currency.toCurrencySymbol()}",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        downDivider = true,
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}