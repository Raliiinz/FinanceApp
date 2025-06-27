package com.example.financeapp.income.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography

/**
 * Компонент отображения общей суммы доходов.
 *
 * @param totalIncome Общая сумма доходов
 */
@Composable
fun TotalIncomeItem(
    totalIncome: Double,
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
                text = formatPrice(totalIncome),
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        downDivider = true,
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}