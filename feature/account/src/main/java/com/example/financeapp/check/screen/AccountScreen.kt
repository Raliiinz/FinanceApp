package com.example.financeapp.check.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.EmptyContent
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.check.components.AccountContent
import com.example.financeapp.check.state.AccountEvent
import com.example.financeapp.check.state.AccountUiState


/**
 *Главный экран отображения счета.
 */
@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onAccountClick: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { viewModel.handleEvent(AccountEvent.Retry) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (uiState) {
            AccountUiState.Loading -> LoadingContent()
            is AccountUiState.Success -> AccountContent(
                account = uiState.account,
                paddingValues = paddingValues,
                onAccountClick = onAccountClick
            )
            is AccountUiState.Error -> {
                ErrorDialog(
                    message = stringResource(uiState.messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = { viewModel.handleEvent(AccountEvent.Retry) },
                    onDismiss = { viewModel.handleEvent(AccountEvent.HideErrorDialog) }
                )
            }
            AccountUiState.Empty -> EmptyContent()
            AccountUiState.Idle -> {}
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

