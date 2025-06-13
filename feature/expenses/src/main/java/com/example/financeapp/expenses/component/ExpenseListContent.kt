package com.example.financeapp.expenses.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import com.example.financeapp.domain.model.Expense


@Composable
fun ExpenseListContent(
    expenses: List<Expense>,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalPrice = expenses.sumOf { it.priceTrailing }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        TotalPriceItem(totalPrice)
        LazyColumn {
            itemsIndexed(expenses) { index, item ->
                ExpenseListItem(
                    expense = item,
                    onClick = { onExpenseClicked(item.id) }
                )
            }
        }
    }
}

@Composable
private fun TotalPriceItem(
    totalPrice: Double,
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
                text = formatPrice(totalPrice.toDouble()),
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
private fun ExpenseListItem(
    expense: Expense,
    onClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            ExpenseLeadingContent(
                icon = expense.iconLeading,
                title = expense.textLeading,
                comment = expense.commentLeading
            )
        },
        trailingContent = {
            ExpenseTrailingContent(price = expense.priceTrailing)
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}

@Composable
private fun ExpenseLeadingContent(
    icon: String,
    title: String,
    comment: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon = icon)
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = title,
                style = Typography.bodyLarge,
                maxLines = 1
            )
            comment?.let {
                Text(
                    text = it,
                    style = Typography.bodyMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ExpenseTrailingContent(
    price: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatPrice(price),
            style = Typography.bodyLarge,
        )
        MoreIcon()
    }
}

@Composable
private fun IconBox(
    icon: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val isEmoji = icon.isSingleEmoji()
        val textStyle = if (isEmoji) {
            MaterialTheme.typography.bodyLarge
        } else {
            MaterialTheme.typography.labelSmall
        }
        Text(
            text = icon,
            color = MaterialTheme.colorScheme.onSurface,
            style = textStyle
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
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
}

private fun String.isSingleEmoji(): Boolean {
    return when {
        length != 2 -> false
        codePointAt(0) in 0x1F600..0x1F64F -> true
        codePointAt(0) in 0x1F300..0x1F5FF -> true
        codePointAt(0) in 0x1F680..0x1F6FF -> true
        codePointAt(0) in 0x2600..0x26FF -> true
        codePointAt(0) in 0x2700..0x27BF -> true
        codePointAt(0) in 0xFE00..0xFE0F -> true
        codePointAt(0) in 0x1F900..0x1F9FF -> true
        else -> false
    }
}
