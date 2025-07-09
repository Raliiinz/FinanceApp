package com.example.financeapp.check.update.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.IconBox
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.util.pattern.BALANCE_PATTERN
import com.example.financeapp.check.model.AccountUIModel

@Composable
fun AccountUpdateItem(
    account: AccountUIModel,
    onNameChange: (String) -> Unit,
    onBalanceChange: (String) -> Unit,
    onCurrencyClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        EditableAccountItem(
            leadingIcon = {
                IconBox(
                    icon = "\uD83D\uDCB0",
                )
            },
            placeholder = stringResource(R.string.check),
            title = stringResource(R.string.account_name),
            value = account.name,
            onValueChange = onNameChange,
        )

        HorizontalDivider()

        EditableAccountItem(
            title = stringResource(R.string.balance),
            value = account.balance,
            placeholder = "0.00",
            keyboardType = KeyboardType.Number,
            onValueChange = onBalanceChange,
            inputFilter = { newValue ->
                newValue.isEmpty() || BALANCE_PATTERN.matches(newValue)
            },
        )

        HorizontalDivider()

        ListItem(
            leadingContent = {
                Text(
                    text = stringResource(R.string.valute),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = account.currency.symbol,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = stringResource(R.string.more_options),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            downDivider = false,
            onClick = onCurrencyClick,
            backgroundColor = MaterialTheme.colorScheme.surface,
            itemHeight = 60.dp
        )

        HorizontalDivider()
    }
}