package com.example.financeapp.history.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.commonItems.MyDatePicker
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.base.ui.util.extension.toCurrencySymbol
import com.example.financeapp.history.components.HistoryListItem
import com.example.financeapp.history.components.InfoItemClickable
import com.example.financeapp.history.state.HistoryEvent
import com.example.financeapp.history.state.HistoryUiState
import com.example.financeapp.navigation.TransactionType
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * Основной экран истории транзакций.
 */
@Composable
fun HistoryScreen(
    transactionType: TransactionType,
    paddingValues: PaddingValues,
    onBackClick: (Int) -> Unit,
    viewModel: HistoryScreenViewModel
) {
    val displayFormatter = remember { DateTimeFormatter.ofPattern("dd-MM-yy") }
    val backendFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

    val fromDateStr by viewModel.fromDate.collectAsState()
    val toDateStr by viewModel.toDate.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val fromDate = remember(fromDateStr) { LocalDate.parse(fromDateStr, backendFormatter) }
    val toDate = remember(toDateStr) { LocalDate.parse(toDateStr, backendFormatter) }

    LaunchedEffect(Unit) {
        viewModel.reduce(HistoryEvent.DateChanged(
            transactionType = transactionType,
            from = fromDateStr,
            to = toDateStr
        ))
    }

    val showStartPicker = remember { mutableStateOf(false) }
    val showEndPicker = remember { mutableStateOf(false) }

    if (showStartPicker.value) {
        MyDatePicker(
            selectedDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                if (!selected.isAfter(toDate)) {
                    viewModel.reduce(HistoryEvent.DateChanged(
                        transactionType = transactionType,
                        from = selected.format(backendFormatter),
                        to = toDateStr
                    ))
                }
            },
            onDismiss = { showStartPicker.value = false },
            maxDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
    }

    if (showEndPicker.value) {
        MyDatePicker(
            selectedDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                if (!selected.isBefore(fromDate) && !selected.isAfter(LocalDate.now())) {
                    viewModel.reduce(HistoryEvent.DateChanged(
                        transactionType = transactionType,
                        from = fromDateStr,
                        to = selected.format(backendFormatter)
                    ))
                }
            },
            onDismiss = { showEndPicker.value = false },
            minDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            maxDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {
        when (val state = uiState) {
            HistoryUiState.Loading -> LoadingContent()
            is HistoryUiState.Error -> {
                ErrorDialog(
                    message = stringResource(state.messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = {
                        viewModel.reduce(HistoryEvent.DateChanged(
                            transactionType = transactionType,
                            from = fromDateStr,
                            to = toDateStr
                        ))
                    },
                    onDismiss = { viewModel.reduce(HistoryEvent.HideErrorDialog) }
                )
            }
            is HistoryUiState.Success -> {
                val historyList = state.transactions
                val totalSum = "${historyList.sumOf { it.amount}} ${state.currency.toCurrencySymbol()}"

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    item {
                        InfoItemClickable(
                            leadingTextResId = R.string.start,
                            trailingText = fromDate.format(displayFormatter),
                            onClick = {
                                showStartPicker.value = true
                            },
                            isDivider = true
                        )
                    }

                    item {
                        InfoItemClickable(
                            leadingTextResId = R.string.end,
                            trailingText = toDate.format(displayFormatter),
                            onClick = {
                                showEndPicker.value = true
                            },
                            isDivider = true
                        )
                    }

                    item {
                        InfoItemClickable(
                            leadingTextResId = R.string.sum,
                            trailingText = totalSum,
                            onClick = {},
                            isDivider = false
                        )
                    }

                    itemsIndexed(historyList) { _, item ->
                        HistoryListItem(item)
                    }
                }
            }

            else -> {}
        }
    }
}
