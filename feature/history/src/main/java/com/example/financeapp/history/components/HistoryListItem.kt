package com.example.financeapp.history.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.IconBox
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.formating.formatBackendTime
import com.example.financeapp.base.ui.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.domain.model.TransactionModel

/**
 * Элемент списка истории транзакций.
 *
 * @param item Данные транзакции
 */
@Composable
fun HistoryListItem(item: TransactionModel) {
    ListItem(
        leadingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconBox(icon = item.categoryEmoji)

                Column(modifier = Modifier
                    .padding(start = 16.dp)) {
                    Text(item.categoryName, style = Typography.bodyLarge, maxLines = 1)
                    item.comment?.let {
                        Text(
                            text = it,
                            style = Typography.labelMedium,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically),
                ) {
                    Text(
                        text = "${item.amount} ${item.currency.toCurrencySymbol()}" +
                                "\n" + formatBackendTime(item.time),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Icon(
                    painterResource(R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        },
        onClick = {},
        downDivider = true,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}