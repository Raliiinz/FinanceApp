package com.example.financeapp.articles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.financeapp.util.ui.isSingleEmoji
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.domain.model.CategoryModel

/**
 * Список категорий статей.
 */
@Composable
fun CategoryListContent(
    categories: List<CategoryModel>,
    onArticleClicked: (Int) -> Unit
) {
    LazyColumn {
        items(categories) { category ->
            CategoryListItem(category, onArticleClicked)
        }
    }
}

@Composable
private fun CategoryListItem(
    category: CategoryModel,
    onArticleClicked: (Int) -> Unit
) {
    ListItem(
        leadingContent = {
            CategoryLeadingContent(category)
        },
        downDivider = true,
        onClick = { onArticleClicked(category.id) },
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp,
        trailingContent = {}
    )
}

@Composable
private fun CategoryLeadingContent(category: CategoryModel) {
    val iconSize = 24.dp
    val iconPaddingEnd = 16.dp

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(end = iconPaddingEnd)
                .size(iconSize)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.iconLeading,
                color = MaterialTheme.colorScheme.onSurface,
                style = if (category.iconLeading.isSingleEmoji()) {
                    MaterialTheme.typography.bodyLarge
                } else {
                    MaterialTheme.typography.labelSmall
                }
            )
        }
        Text(
            text = category.textLeading,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
}    