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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.domain.model.Category

@Composable
fun CategoryListContent(
    categories: List<Category>,
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
    category: Category,
    onArticleClicked: (Int) -> Unit
) {
    val iconSize = 24.dp
    val iconPaddingEnd = 16.dp

    ListItem(
        leadingContent = {
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
                    val isEmoji = category.iconLeading.isSingleEmoji()
                    val textStyle = if (isEmoji) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.labelSmall
                    }
                    Text(
                        text = category.iconLeading,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = textStyle
                    )
                }
                Text(
                    text = category.textLeading,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        trailingContent = {
        },
        downDivider = true,
        onClick = { onArticleClicked(category.id) },
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 70.dp
    )
}

private fun String.isSingleEmoji(): Boolean {
    return when {
        length != 2 -> false
        codePointAt(0) in 0x1F600..0x1F64F -> true
        codePointAt(0) in 0x1F300..0x1F5FF -> true
        codePointAt(0) in 0x1F680..0x1F6FF -> true
        codePointAt(0) in 0x2600..0x26FF -> true
        codePointAt(0) in 0x2700..0x27BF -> true
        codePointAt(0) in 0xFE00..0xFE0F -> true
        codePointAt(0) in 0x1F900..0x1F9FF -> true
        else -> false
    }
}