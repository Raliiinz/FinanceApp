package com.example.financeapp.articles.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.articles.components.CategoryListContent
import com.example.financeapp.articles.components.SearchHeader
import com.example.financeapp.articles.state.CategoryEvent
import com.example.financeapp.articles.state.CategoryScreenUiState
import com.example.financeapp.base.commonItems.ErrorDialog
import com.example.financeapp.base.ui.commonItems.EmptyContent
import com.example.financeapp.base.ui.commonItems.LoadingContent

/**
 * Основной экран отображения категорий статей.
 */
@Composable
fun CategoryScreen(
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onArticleClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
//        SearchHeader(onSearchClicked)

        if (uiState is CategoryScreenUiState.Success) {
            val state = uiState as CategoryScreenUiState.Success
            SearchHeader(
                query = state.query,
                onQueryChange = { viewModel.handleEvent(CategoryEvent.SearchChanged(it)) }
            )
        } else {
            SearchHeader(
                query = "",
                onQueryChange = {  }
            )
        }

        when (val state = uiState) {
            CategoryScreenUiState.Loading -> LoadingContent()
            is CategoryScreenUiState.Success -> {
                if (state.categories.isNotEmpty()) {
                    CategoryListContent(
                        categories = state.filteredCategories,
                        onArticleClicked = onArticleClicked
                    )
                } else {
                    EmptyContent()
                }
            }

            is CategoryScreenUiState.Error -> {
                ErrorDialog(
                    message = stringResource(state.messageRes),
                    retryButtonText = stringResource(R.string.repeat),
                    dismissButtonText = stringResource(R.string.exit),
                    onRetry = { viewModel.handleEvent(CategoryEvent.ReloadData) },
                    onDismiss = { viewModel.handleEvent(CategoryEvent.HideErrorDialog) }
                )
            }

            CategoryScreenUiState.Empty -> EmptyContent()
            CategoryScreenUiState.Idle -> Unit
        }
    }
}
