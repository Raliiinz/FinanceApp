package com.example.financeapp.expenses.component

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
 * Контент элемента расхода (иконка, название, комментарий).
 */
@Composable
fun ExpenseListContent(
    expenses: List<TransactionModel>,
    currency: String,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalPrice = expenses.sumOf { it.amount }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        TotalPriceItem(totalPrice, currency)
        LazyColumn {
            itemsIndexed(expenses) { index, item ->
                ExpenseListItem(
                    transaction = item,
                    onClick = { onExpenseClicked(item.id) }
                )
            }
        }
    }
}
