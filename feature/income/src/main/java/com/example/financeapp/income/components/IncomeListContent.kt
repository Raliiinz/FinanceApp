package com.example.financeapp.income.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.financeapp.domain.model.TransactionModel

/**
 * Список доходов с общей суммой.
 */
@Composable
fun IncomeListContent(
    incomes: List<TransactionModel>,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalIncome = incomes.sumOf { it.amount.toDouble() }

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
                    transaction = item,
                    onClick = { onIncomeClicked(item.id) }
                )
            }
        }
    }
}
