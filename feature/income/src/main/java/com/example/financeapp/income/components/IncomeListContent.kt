package com.example.financeapp.income.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.financeapp.base.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.domain.model.Income


@Composable
fun IncomeListContent(
    incomes: List<Income>,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalIncome = incomes.sumOf { it.priceTrailing.toDouble() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        TotalIncomeItem(totalIncome)
        LazyColumn {
            itemsIndexed(incomes) { index, item ->
                IncomeListItem(
                    income = item,
                    onClick = { onIncomeClicked(item.id) }
                )
            }
        }
    }
}

@Composable
private fun TotalIncomeItem(
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

@Composable
private fun IncomeListItem(
    income: Income,
    onClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            Text(
                text = income.textLeading,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingContent = {
            IncomeTrailingContent(amount = income.priceTrailing)
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 72.dp
    )
}

@Composable
private fun IncomeTrailingContent(
    amount: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatPrice(amount.toDouble()),
            style = Typography.bodyLarge,
        )
        MoreIcon()
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
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
}