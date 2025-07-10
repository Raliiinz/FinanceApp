package com.example.financeapp.check.update.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.widget.Toast
import androidx.compose.foundation.background
import com.example.financeapp.base.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.check.update.components.AccountUpdateItem
import com.example.financeapp.check.update.components.CurrencySelectionBottomSheet
import com.example.financeapp.check.update.state.AccountUpdateEffect
import com.example.financeapp.check.update.state.AccountUpdateEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountUpdateScreen(
    paddingValues: PaddingValues,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: AccountUpdateViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(AccountUpdateEvent.LoadData)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AccountUpdateEffect.ShowToast -> {
                    Toast.makeText(context, context.getString(effect.messageResId), Toast.LENGTH_SHORT).show()
                }
                is AccountUpdateEffect.AccountUpdated -> onSaveClick()
            }
        }
    }


    Column(Modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        Box(
            modifier = Modifier.weight(1f)
            .background(MaterialTheme.colorScheme.surface)
        ) {
            when {
                state.isLoading -> {
                    LoadingContent()
                }
                else -> {
                    AccountUpdateItem(
                        account = state.account,
                        onNameChange = { newName ->
                            viewModel.reduce(AccountUpdateEvent.UpdateName(newName))
                        },
                        onBalanceChange = { newBalance ->
                            viewModel.reduce(AccountUpdateEvent.UpdateBalance(newBalance))
                        },
                        onCurrencyClick = {
                            viewModel.reduce(AccountUpdateEvent.ShowCurrencyBottomSheet)
                        }
                    )
                }
            }
        }
    }

    if (state.showBottomSheet) {
        CurrencySelectionBottomSheet(
            currencies = state.currencies,
            onCurrencySelected = { currency ->
                viewModel.reduce(AccountUpdateEvent.SelectCurrency(currency))
                viewModel.reduce(AccountUpdateEvent.HideCurrencyBottomSheet)
            },
            onDismiss = {
                viewModel.reduce(AccountUpdateEvent.HideCurrencyBottomSheet)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    state.showErrorDialog?.let { message ->
        ErrorDialog(
            message = context.getString(message),
            retryButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onRetry = {
                viewModel.reduce(AccountUpdateEvent.LoadData)
            },
            onDismiss = {
                viewModel.reduce(AccountUpdateEvent.HideErrorDialog)
            }
        )
    }
}