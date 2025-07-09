package com.example.financeapp.check.update.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.check.model.CurrencyUIModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectionBottomSheet(
    currencies: List<CurrencyUIModel>,
    onCurrencySelected: (CurrencyUIModel) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(modifier = modifier) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .heightIn(max = 320.dp)
            ) {
                items(
                    count = currencies.size,
                    key = { index -> currencies[index].currency }
                ) { index ->
                    val currency = currencies[index]
                    ListItem(
                        leadingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(currency.iconResId),
                                    contentDescription = stringResource(currency.nameResId),
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                                Text(
                                    text = stringResource(currency.nameResId),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        },
                        trailingContent = {},
                        downDivider = true,
                        onClick = { onCurrencySelected(currency) },
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        itemHeight = 72.dp
                    )
                }
            }

            // Кнопка закрытия
            ListItem(
                leadingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_exit),
                            contentDescription = stringResource(R.string.exit),
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Text(
                            text = stringResource(R.string.close),
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                trailingContent = {},
                downDivider = false,
                onClick = onDismiss,
                backgroundColor = MaterialTheme.colorScheme.error,
                itemHeight = 72.dp
            )
        }
    }
}