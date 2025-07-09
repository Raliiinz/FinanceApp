package com.example.financeapp.check.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.EmptyContent
import com.example.financeapp.base.ui.commonItems.LoadingContent
import com.example.financeapp.check.main.components.AccountContent
import com.example.financeapp.check.main.state.AccountEffect


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
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.handleEvent(AccountEffect.Retry) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when {
            state.isLoading -> LoadingContent()
            state.account != null -> AccountContent(
                account = state.account,
                paddingValues = paddingValues
            )
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
    state.showErrorDialog?.let { errorRes ->
        ErrorDialog(
            message = stringResource(errorRes),
            retryButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onRetry = { viewModel.handleEvent(AccountEffect.Retry) },
            onDismiss = { viewModel.handleEvent(AccountEffect.HideErrorDialog) }
        )
    }
}

