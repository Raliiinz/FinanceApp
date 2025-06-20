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
import com.example.financeapp.base.commonItems.EmptyContent
import com.example.financeapp.base.commonItems.ErrorContent
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.commonItems.LoadingContent
import com.example.financeapp.check.components.CheckContent
import com.example.financeapp.check.state.AccountEvent
import com.example.financeapp.check.state.AccountUiState

@Composable
fun ChecksScreen(
    viewModel: AccountScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onCheckClicked: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.reduce(AccountEvent.Retry)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (uiState) {
            AccountUiState.Loading -> LoadingContent()
            is AccountUiState.Success -> CheckContent(
                account = uiState.account,
                paddingValues = paddingValues,
                onCheckClicked = onCheckClicked
            )
            is AccountUiState.Error -> {
                ErrorDialog(
                    message = context.getString(uiState.messageRes),
                    confirmButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onConfirm = { viewModel.reduce(AccountEvent.Retry) },
                    onDismiss = { viewModel.reduce(AccountEvent.HideErrorDialog) }
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

