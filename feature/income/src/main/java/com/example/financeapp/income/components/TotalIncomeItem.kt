package com.example.financeapp.income.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol

/**
 * Компонент отображения общей суммы доходов.
 *
 * @param totalIncome Общая сумма доходов
 */
@Composable
fun TotalIncomeItem(
    totalIncome: Double,
    currency: String
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
                text = "${totalIncome.toDouble()} ${currency.toCurrencySymbol()}",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        downDivider = true,
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}