package com.example.financeapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.financeapp.base.ui.commonItems.TopBarTextIcon
import com.example.financeapp.navigation.TopBarConfig
import kotlinx.coroutines.flow.StateFlow

/**
 * Верхняя панель приложения с динамическим контентом в зависимости от текущего экрана.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    topBarStateFlow: StateFlow<TopBarConfig?>
) {
    val topBarState by topBarStateFlow.collectAsState()

    TopAppBar(
        title = {
            topBarState?.let { config ->
                TopBarTextIcon(
                    textResId = config.textResId,
                    leadingImageResId = config.leadingImageResId,
                    onLeadingClicked = config.onLeadingClick,
                    trailingImageResId = config.trailingImageResId,
                    onTrailingClicked = config.onTrailingClick
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
