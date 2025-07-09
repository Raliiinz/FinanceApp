package com.example.financeapp.expenses.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.expenses.component.ExpenseListContent
import com.example.financeapp.expenses.state.ExpensesUiState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.expenses.state.ExpensesEvent

/**
 * Основной экран отображения расходов.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    viewModel: ExpensesScreenViewModel,
    paddingValues: PaddingValues,
    onExpenseClicked: (Int) -> Unit,
    onFabClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTodayExpenses()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (val state = uiState) {
            ExpensesUiState.Loading -> LoadingContent()
            is ExpensesUiState.Success -> ExpenseListContent(
                expenses = state.transactions,
                currency = state.currency,
                paddingValues = paddingValues,
                onExpenseClicked = onExpenseClicked
            )
            is ExpensesUiState.Error -> {
                ErrorDialog(
                    message = stringResource(state.messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = {
                        viewModel.loadTodayExpenses()
                    },
                    onDismiss = { viewModel.handleEvent(ExpensesEvent.HideErrorDialog) }
                )
            }
            ExpensesUiState.Idle -> Unit
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
