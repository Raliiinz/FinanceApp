package com.example.financeapp.transaction.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.ListItem

/**
 * Компонент для отображения и выбора даты/времени.
 *
 * @param titleResId Идентификатор строки заголовка.
 * @param value Отображаемое значение даты или времени.
 * @param onClick Колбэк при нажатии на поле.
 */
@Composable
fun DateTimeSelectionField(
    titleResId: Int,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}