package com.example.financeapp.analysis.navigation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.IconBox
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.domain.model.AnalyticsCategory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AnalysisContent(
    categoryList: List<AnalyticsCategory>,
    currency: String,
    fromDate: LocalDate,
    toDate: LocalDate,
    displayFormatter: DateTimeFormatter,
    paddingValues: PaddingValues,
    showStartPicker: MutableState<Boolean>,
    showEndPicker: MutableState<Boolean>
) {
    val totalSum = categoryList.sumOf { it.totalAmount }
    val totalSumFormatted = "$totalSum ${currency.toCurrencySymbol()}"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        item {
            AnalysisPeriodItem(
                label = stringResource(R.string.period_start),
                date = fromDate,
                displayFormatter = displayFormatter,
                onClick = { showStartPicker.value = true }
            )
        }
        item {
            AnalysisPeriodItem(
                label = stringResource(R.string.period_end),
                date = toDate,
                displayFormatter = displayFormatter,
                onClick = { showEndPicker.value = true }
            )
        }
        item {
            AnalysisSumItem(totalSumFormatted)
        }
        if (categoryList.isEmpty()) {
            item {
                Text(
                    stringResource(R.string.no_data_for_period),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )
            }
        } else {
            itemsIndexed(categoryList) { _, item ->
                AnalysisCategoryItem(item, currency)
            }
        }
    }
}

@Composable
private fun AnalysisPeriodItem(
    label: String,
    date: LocalDate,
    displayFormatter: DateTimeFormatter,
    onClick: () -> Unit
) {
    ListItem(
        leadingContent = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            GreenChip(
                text = date.format(displayFormatter),
                onClick = onClick
            )
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
    )
}

@Composable
private fun AnalysisSumItem(totalSumFormatted: String) {
    ListItem(
        leadingContent = {
            Text(
                stringResource(R.string.sum),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Text(
                totalSumFormatted,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        downDivider = true,
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.surface,
    )
}

@Composable
private fun AnalysisCategoryItem(
    item: AnalyticsCategory,
    currency: String
) {
    ListItem(
        leadingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconBox(icon = item.categoryIcon)

                Text(
                    text = item.categoryName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        },
        trailingContent = {
            Text(
                text = "${item.percent}%\n${item.totalAmount} ${currency.toCurrencySymbol()}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        downDivider = true,
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}
