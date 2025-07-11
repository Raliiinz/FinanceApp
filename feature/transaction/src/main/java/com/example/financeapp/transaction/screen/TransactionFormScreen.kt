package com.example.financeapp.transaction.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.commonItems.MyDatePicker
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.transaction.components.AccountSelectionBottomSheet
import com.example.financeapp.transaction.components.CategorySelectionBottomSheet
import com.example.financeapp.transaction.components.TimePickerDialog
import com.example.financeapp.transaction.components.TransactionFormContent
import com.example.financeapp.transaction.state.TransactionFormEffect.*
import com.example.financeapp.transaction.state.TransactionFormEvent.*
import java.time.ZoneId
import java.util.Date

/**
 * Компонент экрана формы транзакции (добавление или редактирование).
 *
 * @param paddingValues Отступы от вью сверху/снизу (например, от системных баров).
 * @param viewModel ViewModel, управляющая состоянием формы.
 * @param onTransactionSaved Колбэк после успешного сохранения транзакции.
 * @param onClose Колбэк при закрытии экрана.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormScreen(
    paddingValues: PaddingValues,
    viewModel: TransactionFormViewModel,
    onTransactionSaved: () -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShowToast -> {
                    Toast.makeText(context, context.getString(effect.messageResId), Toast.LENGTH_SHORT).show()
                }
                TransactionSaved ->  onTransactionSaved()
                TransactionDeleted -> onTransactionSaved()
            }
        }
    }

    LaunchedEffect(state.validationError) {
        state.validationError?.let { messageResId ->
            Toast.makeText(context, context.getString(messageResId), Toast.LENGTH_SHORT).show()
        }
    }

    if (state.isLoading) {
        LoadingContent(Modifier.fillMaxSize())
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TransactionFormContent(
                state = state,
                handleEvent = viewModel::handleEvent,
                modifier = Modifier.fillMaxSize()
            )
        }

    }

    if (state.showAccountSheet) {
        AccountSelectionBottomSheet(
            accounts = state.availableAccounts,
            onAccountSelected = { viewModel.handleEvent(SelectAccount(it)) },
            onDismiss = { viewModel.handleEvent(HideAccountSheet) }
        )
    }

    if (state.showCategorySheet) {
        CategorySelectionBottomSheet(
            categories = state.availableCategories,
            onCategorySelected = { viewModel.handleEvent(SelectCategory(it)) },
            onDismiss = { viewModel.handleEvent(HideCategorySheet) }
        )
    }

    if (state.showDatePicker) {
        MyDatePicker(
            selectedDate = Date.from(state.date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            onDateSelected = { date ->
                val selectedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                viewModel.handleEvent(UpdateDate(selectedDate))
            },
            onDismiss = { viewModel.handleEvent(HideDatePicker) },
        )
    }

    if (state.showTimePicker) {
        TimePickerDialog(
            initialTime = state.time,
            onTimeSelected = { newTime ->
                viewModel.handleEvent(UpdateTime(newTime))
            },
            onDismiss = {
                viewModel.handleEvent(HideTimePicker)
            },
            containerColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface
        )
    }

    state.errorDialogMessage?.let { messageRes ->
        ErrorDialog(
            message = stringResource(messageRes),
            retryButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onRetry = {
                viewModel.handleEvent(InitForm(state.transactionId, state.type))
            },
            onDismiss = { viewModel.handleEvent(HideErrorDialog) }
        )
    }
}