package com.example.financeapp.check.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.check.model.AccountUIModel
import com.example.financeapp.domain.model.AccountModel

/**
 * Компоненты экрана просмотра счета/аккаунта.
 *
 * Содержит:
 * - [AccountContent] - основной контейнер для отображения информации о счете
 * - [BalanceItem] - элемент отображения баланса счета
 * - [CurrencyItem] - элемент отображения валюты счета
 * - Вспомогательные компоненты [IconBox] и [MoreIcon] для оформления
 */
@Composable
fun AccountContent(
    account: AccountUIModel,
    paddingValues: PaddingValues,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        BalanceItem(account)
        CurrencyItem(account)
    }
}

@Composable
private fun BalanceItem(
    account: AccountUIModel,
) {
    ListItem(
        leadingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(icon = stringResource(R.string.money_icon))
                Text(
                    text = stringResource(R.string.balance),
                    style = Typography.bodyLarge,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${account.balance} ${account.currency.symbol}",
                    style = Typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                MoreIcon()
            }
        },
        downDivider = true,
        onClick = {  },
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}

@Composable
private fun CurrencyItem(
    account: AccountUIModel,
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(R.string.currency),
                style = Typography.bodyLarge,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = account.currency.symbol,
                    style = Typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                MoreIcon()
            }
        },
        downDivider = false,
        onClick = {  },
        backgroundColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun IconBox(
    icon: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .size(24.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            color = Color(0xFFFCE4EB)
        )
    }
}

@Composable
private fun MoreIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_more),
        contentDescription = stringResource(R.string.more_options),
        modifier = modifier.padding(start = 16.dp),
        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
    )
}
