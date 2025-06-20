package com.example.financeapp.articles.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.articles.components.CategoryListContent
import com.example.financeapp.articles.components.SearchHeader
import com.example.financeapp.articles.state.CategoryEvent
import com.example.financeapp.articles.state.CategoryScreenUiState
import com.example.financeapp.base.commonItems.EmptyContent
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.commonItems.LoadingContent

@Composable
fun ArticleScreen(
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onArticleClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.reduce(CategoryEvent.ReloadData)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        SearchHeader(onSearchClicked)

        when (val state = uiState) {
            CategoryScreenUiState.Loading -> LoadingContent()
            is CategoryScreenUiState.Success -> {
                if (state.categories.isNotEmpty()) {
                    CategoryListContent(
                        categories = state.categories,
                        onArticleClicked = onArticleClicked
                    )
                } else {
                    EmptyContent()
                }
            }

            is CategoryScreenUiState.Error -> {
                ErrorDialog(
                    message = context.getString(state.messageRes),
                    confirmButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onConfirm = { viewModel.reduce(CategoryEvent.ReloadData) },
                    onDismiss = { viewModel.reduce(CategoryEvent.HideErrorDialog) }
                )
            }

            CategoryScreenUiState.Empty -> EmptyContent()
            CategoryScreenUiState.Idle -> Unit
        }
    }
}
