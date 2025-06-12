package com.example.financeapp.income.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.domain.model.Income

@Composable
fun IncomeScreen(
    incomeList: List<Income>?,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val incomesListState = incomeList

    val totalPrice = incomesListState?.sumOf { it.priceTrailing } ?: 0.0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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
                        formatPrice(totalPrice),
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

            if (incomesListState != null) {
                LazyColumn {
                    itemsIndexed(incomesListState) { index, item ->
                        ListItem(
                            leadingContent = {
                                Text(
                                    text = item.textLeading,
                                    style = Typography.bodyLarge,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    maxLines = 1
                                )

                            },
                            {
                                Row {
                                    Text(
                                        text = formatPrice(item.priceTrailing.toDouble()),
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
                            onClick = { onIncomeClicked(item.id) },
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            itemHeight = 72.dp
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