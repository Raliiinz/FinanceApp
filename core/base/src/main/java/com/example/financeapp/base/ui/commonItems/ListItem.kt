package com.example.financeapp.base.commonItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Элемент списка с ведущим и завершающим контентом.
 */
@Composable
fun ListItem(
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
    downDivider: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    itemHeight: Dp = 56.dp
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        color = backgroundColor
    ) {
        Column {
            RowContent(leadingContent, trailingContent, itemHeight)
            if (downDivider) ListDivider()
        }
    }
}

@Composable
private fun RowContent(
    leading: @Composable () -> Unit,
    trailing: @Composable () -> Unit,
    height: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) { leading() }
        trailing()
    }
}

@Composable
fun ListDivider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}
