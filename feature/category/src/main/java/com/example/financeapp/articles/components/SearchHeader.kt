package com.example.financeapp.articles.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
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
fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit
) {
    ListItem(
        leadingContent = {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (query.isEmpty()) {
                            Text(
                                text = stringResource(R.string.find_article),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            )
                        }
                        innerTextField()
                    }
                }
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
        onClick = { } ,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    )
}
