package com.example.financeapp.analysis.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financeapp.analysis.navigation.components.AnalysisContent
import com.example.financeapp.analysis.navigation.state.AnalysisEvent
import com.example.financeapp.analysis.navigation.state.AnalysisUiState
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.commonItems.MyDatePicker
import com.example.financeapp.navigation.TransactionType
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun AnalysisScreen(
    viewModel: AnalysisScreenViewModel,
    paddingValues: PaddingValues,
    transactionType: TransactionType
) {
    val uiState by viewModel.uiState.collectAsState()
    val fromDateStr by viewModel.fromDate.collectAsState()
    val toDateStr by viewModel.toDate.collectAsState()

    val backendFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    val displayFormatter = remember { DateTimeFormatter.ofPattern("dd-MM-yy") }

    val fromDate = remember(fromDateStr) { LocalDate.parse(fromDateStr, backendFormatter) }
    val toDate = remember(toDateStr) { LocalDate.parse(toDateStr, backendFormatter) }

    val showStartPicker = remember { mutableStateOf(false) }
    val showEndPicker = remember { mutableStateOf(false) }

    LaunchedEffect(fromDateStr, toDateStr, transactionType) {
        viewModel.loadAnalytics(transactionType, fromDateStr, toDateStr)
    }

    if (showStartPicker.value) {
        MyDatePicker(
            selectedDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selected = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                if (!selected.isAfter(toDate)) {
                    viewModel.reduce(
                        AnalysisEvent.DateChanged(
                            transactionType = transactionType,
                            from = selected.format(backendFormatter),
                            to = toDateStr
                        )
                    )
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
                    viewModel.reduce(
                        AnalysisEvent.DateChanged(
                            transactionType = transactionType,
                            from = fromDateStr,
                            to = selected.format(backendFormatter)
                        )
                    )
                }
            },
            onDismiss = { showEndPicker.value = false },
            minDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            maxDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
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
                ErrorDialog(
                    message = stringResource(id = state.messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = {
                        viewModel.loadAnalytics(transactionType, fromDateStr, toDateStr)
                    },
                    onDismiss = { viewModel.reduce(AnalysisEvent.HideErrorDialog) }
                )
            }

            is AnalysisUiState.Success -> {
                AnalysisContent(
                    categoryList = state.categories,
                    currency = state.currency,
                    fromDate = fromDate,
                    toDate = toDate,
                    displayFormatter = displayFormatter,
                    paddingValues = paddingValues,
                    showStartPicker = showStartPicker,
                    showEndPicker = showEndPicker
                )
            }
            AnalysisUiState.Idle -> {}
        }
    }
}
