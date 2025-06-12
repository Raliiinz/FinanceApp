package com.example.financeapp.expenses.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.domain.model.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    expenseList: List<Expense>?,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val expensesListState = expenseList

    val totalPrice = expensesListState?.sumOf { it.priceTrailing } ?: 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            ListItem(
                leadingContent = {
                    Text(
                        stringResource(R.string.total),
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        style = Typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingContent = {
                    Text(
                        formatPrice(totalPrice.toDouble()),
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        style = Typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                upDivider = false,
                downDivider = true,
                onClick = { },
                backgroundColor = MaterialTheme.colorScheme.secondary,
            )

            if (expensesListState != null) {
                LazyColumn {
                    itemsIndexed(expensesListState) { index, item ->
                        ListItem(
                            leadingContent = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .size(24.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.secondary,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            item.iconLeading,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = item.textLeading,
                                            style = Typography.bodyLarge,
                                            maxLines = 1
                                        )
                                        item.commentLeading?.let {
                                            Text(
                                                text = it,
                                                style = Typography.bodyMedium,
                                                maxLines = 1,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            },
                            {
                                Row {
                                    Text(
                                        text = formatPrice(item.priceTrailing),
                                        style = Typography.bodyLarge,
                                    )
                                    Icon(
                                        painterResource(R.drawable.ic_more_vert),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            },
                            upDivider = false,
                            downDivider = true,
                            onClick = { onExpenseClicked(item.id) },
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            itemHeight = 70.dp
                        )
                    }
                }
            }

        }
        BaseFloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = paddingValues.calculateTopPadding() + 14.dp
                )
        )
    }
}
