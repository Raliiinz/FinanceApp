package com.example.financeapp.income.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.income.components.IncomeListContent
import com.example.financeapp.income.state.IncomeEvent
import com.example.financeapp.income.state.IncomeUiState

/**
 * Основной экран отображения доходов.
 */
@Composable
fun IncomeScreen(
    viewModel: IncomeScreenViewModel,
    paddingValues: PaddingValues,
    onIncomeClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTodayIncomes()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (val state = uiState) {
            IncomeUiState.Loading -> LoadingContent()
            is IncomeUiState.Success -> IncomeListContent(
                incomes = state.incomes,
                currency = state.currency,
                paddingValues = paddingValues,
                onIncomeClicked = onIncomeClicked
            )
            is IncomeUiState.Error -> {
                ErrorDialog(
                    message = stringResource((uiState as IncomeUiState.Error).messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = { viewModel.handleEvent(IncomeEvent.ReloadData) },
                    onDismiss = { viewModel.handleEvent(IncomeEvent.HideErrorDialog) }
                )
            }
            IncomeUiState.Idle -> Unit
        }

        BaseFloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 14.dp
                )
        )
    }
}
