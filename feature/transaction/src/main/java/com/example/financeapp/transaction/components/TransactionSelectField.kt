package com.example.financeapp.transaction.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem

/**
 * Селектор элемента транзакции, отображаемый как строка в списке.
 *
 * @param titleResId Ресурс заголовка поля.
 * @param selectedItemText Текст выбранного элемента (или null, если ничего не выбрано).
 * @param placeholderResId Ресурс строки-заполнителя (placeholder), если элемент не выбран.
 * @param onClick Колбэк при клике по полю.
 */
@Composable
fun TransactionSelectField(
    titleResId: Int,
    selectedItemText: String?,
    placeholderResId: Int,
    onClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedItemText ?: stringResource(placeholderResId),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier.width(24.dp)
                )
            }
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp,
    )
}