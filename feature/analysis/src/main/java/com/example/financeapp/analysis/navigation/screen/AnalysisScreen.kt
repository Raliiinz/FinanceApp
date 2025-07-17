package com.example.financeapp.analysis.navigation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financeapp.analysis.navigation.state.AnalysisEvent
import com.example.financeapp.analysis.navigation.state.AnalysisUiState
import com.example.financeapp.base.R
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.util.formatPrice
import com.example.financeapp.util.showDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalysisScreen(
    viewModelFactory: ViewModelProvider.Factory,
    paddingValues: PaddingValues,
    transactionType: TransactionType
) {
    val viewModel: AnalysisScreenViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val fromDate by viewModel.fromDate.collectAsStateWithLifecycle()
    val toDate by viewModel.toDate.collectAsStateWithLifecycle()

    val context = LocalContext.current

    // При первом запуске грузим аналитику
    LaunchedEffect(fromDate, toDate, transactionType) {
        viewModel.loadAnalytics(transactionType, fromDate, toDate)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (val state = uiState) {
            is AnalysisUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is AnalysisUiState.Error -> {
                ErrorWithRetry(
                    message = stringResource(id = state.messageRes),
                    onRetryClick = {
                        viewModel.loadAnalytics(transactionType, fromDate, toDate)
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is AnalysisUiState.Success -> {
                val categoryList = state.categories
                val totalSum = categoryList.sumOf { it.totalAmount }
                val currency = state.currency
                val totalSumFormatted = formatPrice(totalSum, currency)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    item {
                        PeriodListItem(
                            label = stringResource(R.string.period_start),
                            date = fromDate,
                            onDateClick = {
                                showDatePicker(
                                    initialDate = LocalDate.parse(fromDate),
                                    onDateSelected = {
                                        viewModel.reduce(
                                            AnalysisEvent.DateChanged(
                                                transactionType,
                                                it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                                toDate
                                            )
                                        )
                                    },
                                    context = context
                                )
                            }
                        )
                    }
                    item {
                        PeriodListItem(
                            label = stringResource(R.string.period_end),
                            date = toDate,
                            onDateClick = {
                                showDatePicker(
                                    initialDate = LocalDate.parse(toDate),
                                    onDateSelected = {
                                        viewModel.reduce(
                                            AnalysisEvent.DateChanged(
                                                transactionType,
                                                fromDate,
                                                it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                            )
                                        )
                                    },
                                    minDate = LocalDate.parse(fromDate).toEpochDay() * 24 * 60 * 60 * 1000,
                                    context = context
                                )
                            }
                        )
                    }
                    item {
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
                            verticalPadding = 16.0,
                        )
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
                            ListItem(
                                leadingContent = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .background(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    shape = MaterialTheme.shapes.small
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = item.categoryIcon,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontSize = 10.sp
                                            )
                                        }
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
                                        text = "${item.percent}%\n${formatPrice(item.totalAmount, currency)}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.End,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                downDivider = true,
                                onClick = {},
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                verticalPadding = 10.5
                            )
                        }
                    }
                }
            }
            AnalysisUiState.Idle -> {}
        }
    }
}

@Composable
fun PeriodListItem(
    label: String,
    date: String,
    onDateClick: () -> Unit,
    minDate: Long? = null,
    context: android.content.Context? = null
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
                text = formatLocalDateToMonthYear(LocalDate.parse(date)),
                onClick = onDateClick
            )
        },
        downDivider = true,
        onClick = { },
        backgroundColor = MaterialTheme.colorScheme.surface,
        verticalPadding = 4.0
    )
}

@Composable
fun GreenChip(
    text: String,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = { onClick() },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = MaterialTheme.shapes.large,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = null
    )
}

// Вспомогательные функции (замени на свои реализации)
fun formatLocalDateToMonthYear(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy")
    return date.format(formatter)
}

// ErrorWithRetry и ListItem должны быть реализованы в твоём проекте, как и formatPrice, showDatePicker