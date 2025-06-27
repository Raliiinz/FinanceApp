package com.example.financeapp.articles.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
/**
 * Заголовок поиска для экрана статей.
 */
@Composable
fun SearchHeader(onSearchClicked: () -> Unit) {
    ListItem(
        leadingContent = {
            Text(
                stringResource(R.string.find_article),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = stringResource(R.string.search_articles_description),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        downDivider = true,
        onClick = onSearchClicked,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    )
}